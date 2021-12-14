package com.application.dto;

import com.application.validators.FieldMatch;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
})
public class OrganizationDto {

    @NotEmpty

    private String companyName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    @NotEmpty
    private String url;

    private String description;

    private MultipartFile picByte;

    @Email
    @NotEmpty
    private String email;

    @AssertTrue
    private Boolean terms;
}