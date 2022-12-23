package services;

import data.dtos.request.CreateNewUserRequest;
import data.models.User;

public interface UserService {
    User registerUser(CreateNewUserRequest createNewUserRequest);
    void LogInToDashBoard(String email, String password);
    User getUserByPhoneNumber(String mobileNumber);
}
