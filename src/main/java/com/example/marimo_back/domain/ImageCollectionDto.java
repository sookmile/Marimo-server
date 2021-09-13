package com.example.marimo_back.domain;

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

    private String link;

    private LocalDateTime date;

    private Boolean success;

    private String word;
}
