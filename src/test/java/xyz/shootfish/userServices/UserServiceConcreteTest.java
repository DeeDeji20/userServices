package xyz.shootfish.userServices;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import xyz.shootfish.userServices.data.model.Gender;
import xyz.shootfish.userServices.data.model.User;
import xyz.shootfish.userServices.data.repository.UserRepository;
import xyz.shootfish.userServices.exception.AuthException;
import xyz.shootfish.userServices.exception.UserException;
import xyz.shootfish.userServices.services.UserService;
import xyz.shootfish.userServices.web.payload.request.UpdateProfileRequest;
import xyz.shootfish.userServices.web.payload.response.UserRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
public class UserServiceConcreteTest {
    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    UserRequest request;
    @BeforeEach
    void setUp() {
        //given
        request = UserRequest
                .builder()
                .id("1")
                .firstName("adeola")
                .lastName("oladeji")
                .email("adeolaoladeji@gmail.com")
                .password("deedeji123")
                .confirmPassword("deedeji123")
                .gender(Gender.FEMALE)
                .build();
    }

    @Test
    void testThatUserCanBeCreated(){
        //when
        userService.createUser(request);
        //assert
        assertThat(userService.getAllUser().size(), is(1));

    }

    @Test
    void testThatIfpasswordsDontMatch_throwException(){
        UserRequest request = UserRequest
                .builder()
                .id("1")
                .firstName("adeola")
                .lastName("oladeji")
                .email("adeolaoladeji@gmail.com")
                .password("deedeji123")
                .confirmPassword("deedej]3")
                .gender(Gender.FEMALE)
                .build();
        assertThrows(AuthException.class,()-> userService.createUser(request));
    }

    @Test
    void testThatUserCannotCreateAccountWithEmailThatAlreadyExist_throwException() {
        userService.createUser(request);
        //given
        UserRequest request2 = UserRequest
                .builder()
                .id("2")
                .firstName("mercy")
                .lastName("chioma")
                .email("adeolaoladeji@gmail.com")
                .password("mercySaidNo")
                .confirmPassword("mercySaidNo")
                .gender(Gender.FEMALE)
                .build();

        //assert
        assertThrows(AuthException.class, () -> userService.createUser(request2));
    }

    @Test
    void testThatUserCanFindUserByEmail() {
        //given
        //when
        userService.createUser(request);

        assertThat(userService.getAllUser().size(), is(1));

        User response = userService.getUser(request.getId());
        assertThat(response.getEmail(), is("adeolaoladeji@gmail.com"));
        assertThat(response.getFirstName(),is("adeola"));
        assertThat(response.getLastName(),is("oladeji"));
        assertThat(response.getGender(), is(request.getGender()));

    }

    @Test
    void testThatUserCannotFindUserByEmail_throwException() {
        //when
        userService.createUser(request);
        assertThat(userService.getAllUser().size(), is(1));

        assertThrows(UserException.class,()-> userService.getUser("2"));

    }

    @Test
    void testThatUserCanDeleteUser() {
        //when
        userService.createUser(request);

        //given
        UserRequest request2 = UserRequest
                .builder()
                .id("2")
                .firstName("mercy")
                .lastName("chioma")
                .email("mercy@gmail.com")
                .password("mercySaidNo")
                .confirmPassword("mercySaidNo")
                .gender(Gender.FEMALE)
                .build();

        userService.createUser(request2);

        assertThat(userService.getAllUser().size(), is(2));

        String message = userService.deleteUser("1");

        assertThat(userService.getAllUser().size(), is(1));
        assertThat(message, is("user deleted"));
        assertThat(userService.getAllUser().size(), is(1));

    }

    @Test
    void testThatUserCannotDeleteUserIfNotFound_throwException() {
        //when
        userService.createUser(request);

        assertThat(userService.getAllUser().size(), is(1));

        assertThrows(UserException.class, ()-> userService.deleteUser("2"));

        assertThat(userService.getAllUser().size(), is(1));
    }

    @Test
    void testThatUserCanUpdateUserFirstName() {
        userService.createUser(request);

        assertThat(userService.getAllUser().size(), is(1));

        UpdateProfileRequest updateRequest = new UpdateProfileRequest();
        updateRequest.setFirstName("joel");
        String response = userService.updateUser(request.getId(), updateRequest);

        User user = userService.getUser("1");
//        assert
        assertThat(response, is("profile updated"));
        assertThat(user.getFirstName(),is("joel"));
        assertThat(user.getLastName(),is("oladeji"));
        assertThat(user.getEmail(),is("adeolaoladeji@gmail.com"));
    }

    @Test
    void testThatUserCanUpdateUserLastName() {
        userService.createUser(request);
        assertThat(userService.getAllUser().size(), is(1));

        UpdateProfileRequest updateRequest = new UpdateProfileRequest();
        updateRequest.setLastName("joel");
        String response = userService.updateUser(request.getId(), updateRequest);

        User user = userService.getUser("1");
//        assert
        assertThat(response, is("profile updated"));
        assertThat(user.getFirstName(),is("adeola"));
        assertThat(user.getLastName(),is("joel"));
        assertThat(user.getEmail(),is("adeolaoladeji@gmail.com"));
    }

    @AfterEach
    void tearDown(){
        userService.deleteAllusers();
    }

}
