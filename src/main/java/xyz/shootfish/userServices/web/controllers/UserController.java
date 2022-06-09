package xyz.shootfish.userServices.web.controllers;

//import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.shootfish.userServices.data.model.User;
import xyz.shootfish.userServices.exception.AuthException;
import xyz.shootfish.userServices.exception.UserException;
import xyz.shootfish.userServices.services.UserService;
import xyz.shootfish.userServices.web.payload.request.UpdateProfileRequest;
import xyz.shootfish.userServices.web.payload.response.ApiResponse;
import xyz.shootfish.userServices.web.payload.response.UserRequest;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
//@ApiResponses(value = {
//        @io.swagger.annotations.ApiResponse(code = 400, message = "This is a bad request, please follow the API documentation for the proper request format"),
//        @io.swagger.annotations.ApiResponse(code = 401, message = "Due to security constraints, your access request cannot be authorized"),
//        @io.swagger.annotations.ApiResponse(code = 500, message = "The server is down. Please bear with us."),
//}
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequest request){

        try{
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(true)
                    .message(""+ userService.createUser(request))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(AuthException e){
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(false)
                    .message(e.getMessage())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id){
        try{

            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(true)
                    .message("" + userService.getUser(id))
                    .build();

            return  new ResponseEntity<>(response, HttpStatus.OK);
        }catch(UserException ex){
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(false)
                    .message(ex.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }

    @GetMapping("/deleteUser/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        try{
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(true)
                    .message("" + userService.deleteUser(id))
                    .build();

            return  new ResponseEntity<>(response, HttpStatus.OK);

        }catch (UserException ex){
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(false)
                    .message(ex.getMessage())
                    .build();

            return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UpdateProfileRequest request){
        try{
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(true)
                    .message("" + userService.updateUser(id, request))
                    .build();

            return  new ResponseEntity<>(response, HttpStatus.OK);

        }catch (UserException ex){
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(false)
                    .message(ex.getMessage())
                    .build();
            return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
