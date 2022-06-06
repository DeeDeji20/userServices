package xyz.shootfish.userServices.services;

//import com.shootfish.task.userRestApplication.data.models.User;
//import com.shootfish.task.userRestApplication.dtos.request.CreateUserRequest;
//import com.shootfish.task.userRestApplication.dtos.request.UpdateProfileRequest;
//import com.shootfish.task.userRestApplication.dtos.response.FindUserResponse;
//import com.shootfish.task.userRestApplication.dtos.response.UserDto;
import xyz.shootfish.userServices.data.model.User;
import xyz.shootfish.userServices.web.payload.request.UpdateProfileRequest;
import xyz.shootfish.userServices.web.payload.response.UserProfile;
import xyz.shootfish.userServices.web.payload.response.UserRequest;

import java.util.Collection;
import java.util.List;


public interface UserService {
    UserProfile createUser(UserRequest request);

    List<User> getAllUser();

    void deleteAllusers();

    User getUser(String id);

    String deleteUser(String id);

    String updateUser(String id, UpdateProfileRequest updateRequest);

//    List<User> getAllUser();
//
//    void deleteAll();
//
//    FindUserResponse findUser(String email);
//
//
//    String  deleteUser(String email);
//
//    String updateUser(String email, UpdateProfileRequest updateRequest);
}
