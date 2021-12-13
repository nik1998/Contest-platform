package com.application.dto;

import com.application.model.Organization;
import com.application.model.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestDto {

    private long id;
    @NotEmpty
    private String contestName;
    private String description;
    @NotEmpty
    private String categories;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date deadline;
    private Integer prize;

    private Organization organization;

    private Set<User> jury = new HashSet<>();
}
