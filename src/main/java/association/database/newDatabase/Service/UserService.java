package association.database.newDatabase.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import association.database.newDatabase.Entity.AddressModel;
import association.database.newDatabase.Entity.IdCardModel;
import association.database.newDatabase.Entity.UserModel;
import association.database.newDatabase.Exception.DataValidationException;
import association.database.newDatabase.Exception.UserAlreadyExistsException;
import association.database.newDatabase.Repository.IdCardRepository;
import association.database.newDatabase.Repository.UserRepository;
import association.database.newDatabase.Exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    UserRepository userRepository;
    @Autowired
    IdCardService idCardService;
    @Autowired
    AddressService addressService;

    public UserModel createUser(UserModel userdata) {
        try {
            if(userRepository.existsByEmail(userdata.getEmail())){
                throw new UserAlreadyExistsException("User already exists with email: " + userdata.getEmail());
            }
            if (!userdata.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new DataValidationException("Email isn't satisfied", null);
            }
            String pass = userdata.getPassword();
            if(!pass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")){
                throw new DataValidationException("Password Doesn't matched", null);
            }

            List<AddressModel> addresses = userdata.getAddressModel();
            if (addresses != null) {
                for (AddressModel address : addresses) {
                    address.setUser(userdata);
                }
            }

            UserModel savedUser = userRepository.save(userdata);
            IdCardModel Idcard = idCardService.createIdCard(savedUser);
            
            savedUser.setICardModel(Idcard);
            return userRepository.save(savedUser);

        } catch (UserAlreadyExistsException e) {
            logger.error("Error: {}", e.getMessage());
            throw e;
        } catch(DataValidationException e){
            logger.error("Error: {}", e.getMessage());
            throw e;
        }
    }

    // Admin Method
    public List<UserModel> listAllUser(){
        return userRepository.getAllUser();
    }

    public List<UserModel> printAllUsers() {
        List<UserModel> allUsers = listAllUser();
        allUsers.forEach(user -> {
            System.out.println(user);
        });
        return allUsers;
    }



    public UserModel updateUser(int id, UserModel userData) {
        UserModel existingUser  = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User  not found with id: " + id));
        if(!userData.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")){
            throw new DataValidationException("Password Doesn't matched", null);
        }
        if (userData.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$")) {
            throw new DataValidationException("Email isn't satisfied", null);
        }
        existingUser.setEmail(userData.getEmail());
        existingUser.setName(userData.getName());
        existingUser.setPassword(userData.getPassword());
        return userRepository.save(existingUser);
    }

    public UserModel currentUser(int id) {
        UserModel user = userRepository.getUserByID(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return user;
    }

    public String deleteAccount(int id) {
        UserModel user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.deleteById(id);
        return "User with ID " + id + " deleted successfully.";
    }
    
}
