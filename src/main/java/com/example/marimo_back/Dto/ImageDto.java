package com.example.marimo_back.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {

    private String link;

    private String gsURI;

    private String word;
}
