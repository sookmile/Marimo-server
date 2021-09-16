package com.example.marimo_back.Dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageCollectionDto {

    private Long id;

    private String link;

    private LocalDateTime date;

    private Boolean success;

    private String word;
}
