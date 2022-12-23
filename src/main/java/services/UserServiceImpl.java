package services;

import data.dtos.repositories.AddressRepository;
import data.dtos.repositories.UserRepository;
import data.dtos.request.CreateNewUserRequest;
import data.models.Address;
import data.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public User registerUser(CreateNewUserRequest createNewUserRequest) {
        var newUser = new User();
        var userAddress = Address.builder()
                        .city(createNewUserRequest.getAddress().getCity())
                        .country(createNewUserRequest.getAddress().getCountry())
                        .state(createNewUserRequest.getAddress().getState())
                        .street(createNewUserRequest.getAddress().getStreet())
                        .build();
        addressRepository.save(userAddress);

        newUser.setFirstName(createNewUserRequest.getFirstName());
        newUser.setLastName(createNewUserRequest.getLastName());
        newUser.setEmail(createNewUserRequest.getEmail());
        newUser.setAddress(userAddress);
        newUser.setPassword(createNewUserRequest.getPassword());
        newUser.setPhoneNumber(createNewUserRequest.getMobileNumber());
       return userRepository.save(newUser);
    }

    @Override
    public void LogInToDashBoard(String email, String password) {

    }

    @Override
    public User getUserByPhoneNumber(String mobileNumber) {
        return userRepository.findUserByPhoneNumber(mobileNumber);
    }
}
