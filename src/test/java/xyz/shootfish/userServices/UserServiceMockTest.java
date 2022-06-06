package xyz.shootfish.userServices;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import xyz.shootfish.userServices.data.model.User;
import xyz.shootfish.userServices.data.repository.UserRepository;
import xyz.shootfish.userServices.web.payload.response.UserRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UserServiceMockTest {

    private User mockedUser;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        mockedUser = new User();
        mockedUser.setId("1");
        mockedUser.setFirstName("Deola");
        mockedUser.setLastName("Deji");
        mockedUser.setEmail("dee@gmail.com");
        mockedUser.setPassword("pass1234");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void userCanRegister(){
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("dee@gmail.com");
        when(userRepository.existsById(anyString())).thenReturn(false);
        when(modelMapper.map(userRequest, User.class)).thenReturn(mockedUser);
        when(userRepository.save(any(User.class))).thenReturn(mockedUser);
    }
}
