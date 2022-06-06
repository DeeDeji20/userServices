package xyz.shootfish.userServices.web.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.shootfish.userServices.data.model.Gender;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private String email;
    private Gender gender;
}
