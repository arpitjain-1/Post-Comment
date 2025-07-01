package association.database.newDatabase.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
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
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@CacheConfig(cacheNames = "users") 
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private IdCardService idCardService;
    
    public String createUser(UserCreateDTO userData) {
        try {
            if (userRepository.existsByEmail(userData.getEmail())) {
                throw new UserAlreadyExistsException("User already exists with email: " + userData.getEmail());
            }

            if (!userData.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new DataValidationException("Invalid email format", null);
            }

            if (!userData.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
                throw new DataValidationException("Password doesn't meet complexity requirements", null);
            }

            UserModel user = new UserModel();
            user.setName(userData.getName());
            user.setEmail(userData.getEmail());
            user.setPassword(userData.getPassword());

            List<AddressModel> addressList = new ArrayList<>();
            if (userData.getAddressModel() != null) {
                for (AddressDTO dto : userData.getAddressModel()) {
                    AddressModel address = new AddressModel();
                    address.setCity(dto.getCity());
                    address.setStreet(dto.getStreet());
                    address.setCountry(dto.getCountry());
                    address.setUser(user);
                    addressList.add(address);
                }
            }
            user.setAddressModel(addressList);

            UserModel savedUser = userRepository.save(user);
            IdCardModel idCard = idCardService.createIdCard(savedUser);
            savedUser.setICardModel(idCard);
            userRepository.save(savedUser);

            return "User Created Successfully";

        } catch (UserAlreadyExistsException | DataValidationException e) {
            logger.error("Error creating user: {}", e.getMessage());
            throw e;
        }
    }

    @Cacheable(value = "Users")
    public List<UserModel> listAllUser() {
        logger.info("Fetching all users from database");
        return userRepository.getAllUser();
    }

    @Cacheable(value = "User", key = "#id")
    public UserResponseDTO currentUser(int id) {
        logger.info("Fetching user {} from database", id);
        UserModel user = userRepository.getUserByID(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return mapToUserResponseDTO(user);
    }

    @CachePut(value = "User", key = "#id")
    public UserResponseDTO updateUser(int id, UserCreateDTO userData) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (userData.getPassword() != null && 
            !userData.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            throw new DataValidationException("Password doesn't meet complexity requirements", null);
        }

        if (userData.getEmail() != null && 
            !userData.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new DataValidationException("Invalid email format", null);
        }

        if (userData.getName() != null) existingUser.setName(userData.getName());
        if (userData.getEmail() != null) existingUser.setEmail(userData.getEmail());
        if (userData.getPassword() != null) existingUser.setPassword(userData.getPassword());

        if (userData.getAddressModel() != null) {
            Map<Integer, AddressModel> existingAddressMap = existingUser.getAddressModel().stream()
                    .collect(Collectors.toMap(AddressModel::getAddressId, addr -> addr));

            for (AddressDTO addressDTO : userData.getAddressModel()) {
                if (addressDTO.getAddressId() != null) {
                    AddressModel existingAddress = existingAddressMap.get(addressDTO.getAddressId());
                    if (existingAddress != null) {
                        if (addressDTO.getCity() != null) existingAddress.setCity(addressDTO.getCity());
                        if (addressDTO.getCountry() != null) existingAddress.setCountry(addressDTO.getCountry());
                        if (addressDTO.getStreet() != 0) existingAddress.setStreet(addressDTO.getStreet());
                    }
                } else {
                    AddressModel newAddress = new AddressModel();
                    newAddress.setCity(addressDTO.getCity());
                    newAddress.setCountry(addressDTO.getCountry());
                    newAddress.setStreet(addressDTO.getStreet());
                    newAddress.setUser(existingUser);
                    existingUser.getAddressModel().add(newAddress);
                }
            }
        }

        UserModel savedUser = userRepository.save(existingUser);
        return mapToUserResponseDTO(savedUser);
    }

    @CacheEvict(value = "User", key = "#id")
    public String deleteAccount(int id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        userRepository.delete(user);
        return "User with ID " + id + " deleted successfully.";
    }
    
    private UserResponseDTO mapToUserResponseDTO(UserModel user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        if (user.getAddressModel() != null) {
            List<AddressResponseDTO> addresses = user.getAddressModel().stream()
                    .map(address -> {
                        AddressResponseDTO addrDto = new AddressResponseDTO();
                        addrDto.setAddressId(address.getAddressId());
                        addrDto.setCity(address.getCity());
                        addrDto.setCountry(address.getCountry());
                        return addrDto;
                    })
                    .collect(Collectors.toList());
            dto.setAddressModel(addresses);
        }

        return dto;
    }
}