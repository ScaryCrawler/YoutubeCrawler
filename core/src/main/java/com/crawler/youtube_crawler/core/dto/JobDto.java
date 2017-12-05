package com.crawler.youtube_crawler.core.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "jobs")
@Data
public class JobDto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
    private Long id;

    @NotBlank
    private String status;
}
