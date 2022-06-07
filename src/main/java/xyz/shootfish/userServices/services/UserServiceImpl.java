package xyz.shootfish.userServices.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.shootfish.userServices.data.model.User;
import xyz.shootfish.userServices.data.repository.UserRepository;
import xyz.shootfish.userServices.exception.AuthException;
import xyz.shootfish.userServices.exception.UserException;
import xyz.shootfish.userServices.web.payload.request.UpdateProfileRequest;
import xyz.shootfish.userServices.web.payload.response.UserProfile;
import xyz.shootfish.userServices.web.payload.response.UserRequest;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserProfile createUser(UserRequest request) {
//        if (validateUser(request.getId())) throw new AuthException("Email already exist");
        if (validateEmail(request.getEmail())) throw new AuthException("Email already exist");
        if(!request.getPassword().matches(request.getConfirmPassword())) throw new AuthException("password must match");
        User user = modelMapper.map(request, User.class);
        userRepository.save(user);
        return modelMapper.map(user, UserProfile.class);
    }

    private boolean validateEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteAllusers() {
        userRepository.deleteAll();
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(()-> new UserException("User does not exist"));
    }

    @Override
    public String deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserException("User not found"));
        userRepository.deleteById(id);
        return "user deleted";
    }

    @Override
    public String updateUser(String id, UpdateProfileRequest updateRequest) {
        User user = userRepository.findById(id).orElseThrow(()->new UserException("user not found"));
        modelMapper.map(updateRequest, user);
        userRepository.save(user);
        return "profile updated";
    }

    private boolean validateUser(String id) {
        return userRepository.existsById(id);
    }
}
