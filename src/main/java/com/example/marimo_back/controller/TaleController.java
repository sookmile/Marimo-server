package com.example.marimo_back.controller;


import com.example.marimo_back.Dto.GameResultRequestDto;
import com.example.marimo_back.Dto.TaleDataRequestDto;
import com.example.marimo_back.Dto.TaleResultDataDto;
import com.example.marimo_back.service.TaleService;
import com.example.marimo_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TaleController {

    private final TaleService taleService;


    @ResponseBody
    @PostMapping("marimo/tale/save")
    public String saveTale(@RequestBody TaleResultDataDto dto){

        System.out.println(dto.getTaleName()+dto.getLastpage()+dto.getUserId());

        taleService.saveResult(dto);

        return "Success";
    }

    @ResponseBody
    @PostMapping("marimo/tale/feedback")
    public String saveTaleAndFeedBack (@RequestBody TaleDataRequestDto dto) {

        System.out.println(dto.getOWord()+dto.getRWord()+dto.getLastpage());
        return taleService.saveTaleFeedback(dto);
    }

    @ResponseBody
    @PostMapping("marimo/tale/speechuri")
    public Map<String, String> sendSpeeachUri (@RequestBody Map<String,String> oWord) {

        String[] requestList={"침대", "이불", "시계", "축구공", "액자", "냉장고", "사과", "포도", "수박", "바나나", "달력", "기차", "자전거", "색연필", "도토리", "장갑"};
        String[] requestLinks= {
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%8E%E1%85%B5%E1%86%B7%E1%84%83%E1%85%A2.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%8B%E1%85%B5%E1%84%87%E1%85%AE%E1%86%AF.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A5_%E1%84%89%E1%85%B5%E1%84%80%E1%85%A8.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%8E%E1%85%AE%E1%86%A8%E1%84%80%E1%85%AE%E1%84%80%E1%85%A9%E1%86%BC.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A5_%E1%84%8B%E1%85%A2%E1%86%A8%E1%84%8C%E1%85%A1.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A5_%E1%84%82%E1%85%A2%E1%86%BC%E1%84%8C%E1%85%A1%E1%86%BC%E1%84%80%E1%85%A9.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%89%E1%85%A1%E1%84%80%E1%85%AA.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A5_%E1%84%91%E1%85%A9%E1%84%83%E1%85%A9.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%89%E1%85%AE%E1%84%87%E1%85%A1%E1%86%A8.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%87%E1%85%A1%E1%84%82%E1%85%A1%E1%84%82%E1%85%A1.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A5_%E1%84%83%E1%85%A1%E1%86%AF%E1%84%85%E1%85%A7%E1%86%A8.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%80%E1%85%B5%E1%84%8E%E1%85%A1.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%8C%E1%85%A1%E1%84%8C%E1%85%A5%E1%86%AB%E1%84%80%E1%85%A5.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%89%E1%85%A2%E1%86%A8%E1%84%8B%E1%85%A7%E1%86%AB%E1%84%91%E1%85%B5%E1%86%AF.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%A5_%E1%84%83%E1%85%A9%E1%84%90%E1%85%A9%E1%84%85%E1%85%B5.mp4",
                "https://storage.googleapis.com/marimo_bucket/video/%E1%84%8C%E1%85%A1%E1%86%BC%E1%84%80%E1%85%A1%E1%86%B8.mp4",
        };

        Map<String, String> uri=new LinkedHashMap<>();

        for(int i=0; i<requestList.length; i++){
            if(oWord.get("word").equals(requestList[i])){
                System.out.println(requestList[i]);
                uri.put("uri", requestLinks[i]);
            }
        }

        return uri;
    }

}
