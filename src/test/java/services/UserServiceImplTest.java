package services;

import com.verse.verselogistics.VerseLogisticsApplication;
import data.dtos.request.CreateNewUserRequest;
import data.models.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = VerseLogisticsApplication.class)
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    private CreateNewUserRequest createNewUserRequest;
    private Address address;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .city("old york")
                .country("united state of UK")
                .state("Iyana-ira")
                .street("20 old bread street")
                .build();

          createNewUserRequest = new CreateNewUserRequest(
                "Dende", "makudi", "dendeMakudi@gmail.com",
                "dendeDdon", "07011223344", address);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testThatUserCanSignUp(){
       var registeredUser =  userService.registerUser(createNewUserRequest);
        assertEquals(registeredUser, userService.getUserByPhoneNumber(registeredUser.getPhoneNumber()));
    }
}