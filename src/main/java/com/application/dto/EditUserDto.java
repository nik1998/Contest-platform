package com.application.dto;

import com.application.model.Contest;
import com.application.model.User;
import com.application.validators.FieldMatch;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.*;


@Data
@NoArgsConstructor
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
})
public class EditUserDto {
    @NotEmpty
    private String firstName;

    private String lastName;

    private String password;

    private String confirmPassword;

    private Integer rating;

    private Set<Contest> contests = new HashSet<>();

    @Email
    @NotEmpty
    private String email;

    private Map<String, String> contacts = new HashMap<>();

    public EditUserDto(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.contacts = user.getContacts();
        this.email = user.getEmail();
        this.rating = user.getRating();
        this.contests = user.getContests();
    }
}
