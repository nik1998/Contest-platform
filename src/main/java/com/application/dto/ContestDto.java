package com.application.dto;

import com.application.model.Organization;
import com.application.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
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
    private LocalDateTime startDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;

    //P3DT5H40M30S
    private Duration endVoteDate;

    private String prize;

    private Organization organization;

    private Set<User> jury = new HashSet<>();

    public Boolean popularVoting = false;
}
