package com.application.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContestDto {
    @NotEmpty
    private String contestName;
    private String description;
    @NotEmpty
    private String category;
    @NotEmpty
    private String startDate;
    @NotEmpty
    private String deadline;
    private Integer prize;
}
