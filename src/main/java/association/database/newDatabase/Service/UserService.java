package association.database.newDatabase.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import association.database.newDatabase.DTO.Request.AddressDTO;
import association.database.newDatabase.DTO.Request.UserCreateDTO;
import association.database.newDatabase.DTO.Response.AddressResponseDTO;
import association.database.newDatabase.DTO.Response.UserResponseDTO;
import association.database.newDatabase.Entity.AddressModel;
import association.database.newDatabase.Entity.IdCardModel;
import association.database.newDatabase.Entity.UserModel;
import association.database.newDatabase.Exception.DataValidationException;
import association.database.newDatabase.Exception.UserAlreadyExistsException;
import association.database.newDatabase.Repository.UserRepository;
import association.database.newDatabase.Exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public String createUser(UserCreateDTO userdata) {
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

            UserModel User = new UserModel();
            User.setName(userdata.getName());
            User.setEmail(userdata.getEmail());
            User.setPassword(userdata.getPassword());

            List<AddressModel> addressList = new ArrayList<>();
            if (userdata.getAddressModel() != null) {
                for (AddressDTO dto : userdata.getAddressModel()) {
                    AddressModel address = new AddressModel();
                    address.setCity(dto.getCity());
                    address.setStreet(dto.getStreet());
                    address.setCountry(dto.getCountry());
                    address.setUser(User);
                    addressList.add(address);
                }
            }            
            
            User.setAddressModel(addressList);

            IdCardModel Idcard = idCardService.createIdCard(userRepository.save(User));
            User.setICardModel(Idcard);
            
            userRepository.save(User);
            return "User Created";

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
        if (!userData.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new DataValidationException("Email isn't satisfied", null);
        }
        existingUser.setEmail(userData.getEmail());
        existingUser.setName(userData.getName());
        existingUser.setPassword(userData.getPassword());
        return userRepository.save(existingUser);
    }

    public UserResponseDTO currentUser(int id) {
        UserModel user = userRepository.getUserByID(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        UserResponseDTO UserDTO = new UserResponseDTO();
        UserDTO.setId(user.getId());
        UserDTO.setEmail(user.getEmail());
        UserDTO.setName(user.getName());

        List <AddressResponseDTO> addresses = user.getAddressModel().stream()
            .map(address -> {
                AddressResponseDTO a = new AddressResponseDTO();
                a.setCity(address.getCity());
                a.setCountry(address.getCountry());
                return a;
            }).collect(Collectors.toList());
        
        UserDTO.setAddressModel(addresses);
        return UserDTO;
    }

    public String deleteAccount(int id) {
        userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.deleteById(id);
        return "User with ID " + id + " deleted successfully.";
    }
    
}
