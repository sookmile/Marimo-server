package com.example.marimo_back.service;

import com.example.marimo_back.Dto.ImageCollectionDto;
import com.example.marimo_back.Dto.ImageDto;
import com.example.marimo_back.domain.Images;
import com.example.marimo_back.domain.Users;
import com.example.marimo_back.repository.ImageRepository;
import com.example.marimo_back.repository.UserRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.vision.v1.*;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobTargetOption;
import com.google.cloud.storage.Storage.PredefinedAcl;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public ImageDto uploadImage(MultipartFile file) throws IOException {
        // gcp cloud storage 버킷에 파일 업로드
        try {
            String keyFileName = "static/top-glass-322515-5fcccf54600d.json";
            InputStream keyfile = ResourceUtils.getURL("classpath:" + keyFileName).openStream();
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(keyfile))
                    .build().getService();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd"); // 폴더 이름 업로드 날짜로 바꾸기
            String folderName = simpleDateFormat.format(new Date());
            String objectName = Long.toString(System.nanoTime()); // 파일명 변경

            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder("marimo_bucket", folderName + "/" + objectName).build(), //get original file name
                    file.getBytes(), // the file
                    BlobTargetOption.predefinedAcl(PredefinedAcl.PUBLIC_READ) // Set file permission
            );
//            System.out.println(blobInfo.getMediaLink());
            return ImageDto.builder().gsURI(folderName + "/" + objectName).link(blobInfo.getMediaLink()).build();
//            return folderName + "/" + objectName;
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageDto detectLocalizedObjectsGcs(ImageDto imageDto) throws IOException {
        // 이미지 인식
        String gcsPath = "gs://marimo_bucket/" + imageDto.getGsURI();
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
        Image img = Image.newBuilder().setSource(imgSource).build();

        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder()
                        .addFeatures(Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION))
                        .setImage(img)
                        .build();
        requests.add(request);

        String word = "";

        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            // Perform the request
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();
            client.close();
            // Display the results
            for (AnnotateImageResponse res : responses) {
                for (LocalizedObjectAnnotation entity : res.getLocalizedObjectAnnotationsList()) {
                    System.out.format("Object name: %s%n", entity.getName());
                    System.out.format("Confidence: %s%n", entity.getScore());
                    System.out.format("Normalized Vertices:%n");
                    entity
                            .getBoundingPoly()
                            .getNormalizedVerticesList()
                            .forEach(vertex -> System.out.format("- (%s, %s)%n", vertex.getX(), vertex.getY()));

//                    System.out.println(entity.getName());
                    // 영어 -> 한글 번역
                    Translate translate = TranslateOptions.getDefaultInstance().getService();
                    Translation translation = translate.translate(entity.getName(), Translate.TranslateOption.targetLanguage("ko"));
                    word = translation.getTranslatedText();
                    imageDto.setWord(word);
                    System.out.println(translation.getTranslatedText());
                }
            }
        }

        return imageDto;
    }

    public void saveUserImage(Map<Object, String> imageInfo) {
        Long userId = Long.parseLong(imageInfo.get("userId"));
        String link = imageInfo.get("link");
        String word = imageInfo.get("word");

        Users user = userRepository.findById(userId);

        List<Images> findImage = imageRepository.findImage(link);
        if (findImage.size() > 0)
            return;

        Images image = Images.builder().user(user).link(link).word(word).date(LocalDateTime.now()).build();
        imageRepository.save(image);
    }

    public List<ImageCollectionDto> showImages(Map<Object, String> imageInfo) {
        Long userId = Long.parseLong(imageInfo.get("userId"));
        Users user = userRepository.findById(userId);

        List<Images> imageList = imageRepository.findByUserId(user);
        ArrayList<ImageCollectionDto> resultList = new ArrayList<>();
        for (Images img : imageList) {
            ImageCollectionDto dto = ImageCollectionDto.builder().id(img.getId()).link(img.getLink()).date(img.getDate()).success(img.getSuccess()).word(img.getWord()).build();
            resultList.add(dto);
        }
        return resultList;
    }
}
