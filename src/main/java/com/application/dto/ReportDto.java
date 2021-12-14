package com.application.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ReportDto {

    @NotNull
    private Long contestId;

    @NotEmpty
    private String text;

    private MultipartFile fileReport;

}
