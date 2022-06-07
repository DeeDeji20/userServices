package xyz.shootfish.userServices.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("User")
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    @Email
    @NotNull
    @NotBlank
    private String email;
    private Gender gender;
}
