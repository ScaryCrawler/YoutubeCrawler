package com.crawler.youtube_crawler.core.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "results")
@Data
public class ResultDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
    private Long id;

    @ManyToOne
    private JobDto job;
}
