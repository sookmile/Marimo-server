package com.example.marimo_back.controller;

import com.example.marimo_back.domain.ImageCollectionDto;
import com.example.marimo_back.domain.ImageDto;
import com.example.marimo_back.domain.Images;
import com.example.marimo_back.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    private final ImageService imageService;

    /**
     * Detects localized objects in a remote image on Google Cloud Storage.
     *
     * @param gcsPath The path to the remote file on Google Cloud Storage to detect localized objects
     *     on.
     * @throws Exception on errors while closing the client.
     * @throws IOException on Input/Output errors.
     */
    @PostMapping("/image/name")
    public ImageDto findImageName(@RequestPart(value = "image", required = false) MultipartFile file) throws IOException {
        ImageDto imageDto = imageService.uploadImage(file);
        return imageService.detectLocalizedObjectsGcs(imageDto);
    }

    @PostMapping("/image/save")
    @ResponseBody
    public String saveImage(@RequestBody Map<Object, String> imageInfo) {
        imageService.saveUserImage(imageInfo);
        return "success";
    }

    @PostMapping("/image/show")
    @ResponseBody
    public List<ImageCollectionDto> imageCollection(@RequestBody Map<Object, String> userInfo) {
        return imageService.showImages(userInfo);
    }

/*    @ConfigurationProperties("spring.cloud.gcp.vision")
    static void authExplicit(String jsonPath) throws IOException {
        // You can specify a credential file by providing a path to GoogleCredentials.
        // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        System.out.println("Buckets:");
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }
    }*/
}
