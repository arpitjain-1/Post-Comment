package association.database.newDatabase.Utils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import association.database.newDatabase.DTO.Request.AddressDTO;
import association.database.newDatabase.DTO.Request.UserCreateDTO;
import association.database.newDatabase.Entity.AddressModel;
import association.database.newDatabase.Entity.UserModel;

public class TestDataFactory {

    public static UserCreateDTO createValidUserDTO() {
        UserCreateDTO UserDTO = new UserCreateDTO();
        UserDTO.setEmail("arpit" + UUID.randomUUID() + "@gmail.com");
        UserDTO.setPassword("Arpit@0211");
        UserDTO.setName("Arpit");
        
        AddressDTO address1 = new AddressDTO();
        address1.setCity("Delhi");
        address1.setCountry("India");
        address1.setStreet(10);
        
        AddressDTO address2 = new AddressDTO();
        address2.setCity("Indore");
        address2.setCountry("India");
        address2.setStreet(1);
        
        UserDTO.setAddressModel(List.of(address1, address2));
        return UserDTO;
    }

    public static UserCreateDTO createUpdateUserPayload() {
        UserCreateDTO UpdateUserDTO = new UserCreateDTO();
        UpdateUserDTO.setEmail("arpit" + UUID.randomUUID() + "@jain.com");
        UpdateUserDTO.setPassword("Password@0211");
        UpdateUserDTO.setName("Arpit");
        
        AddressDTO UpdateUserAddress1 = new AddressDTO();
        UpdateUserAddress1.setCity("Delhi");
        UpdateUserAddress1.setCountry("India");
        UpdateUserAddress1.setStreet(10);
        
        AddressDTO UpdateUserAddress2 = new AddressDTO();
        UpdateUserAddress2.setCity("Shivpuri");
        UpdateUserAddress2.setCountry("India");
        UpdateUserAddress2.setStreet(1);
        
        UpdateUserDTO.setAddressModel(List.of(UpdateUserAddress1, UpdateUserAddress2));
        return UpdateUserDTO;
    }
    
    public static UserModel mapToUserEntity(UserCreateDTO dto) {
        UserModel user = new UserModel();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        List<AddressModel> addresses = dto.getAddressModel().stream()
            .map(addressDTO -> {
                AddressModel address = new AddressModel();
                address.setStreet(addressDTO.getStreet());
                address.setCity(addressDTO.getCity());
                address.setCountry(addressDTO.getCountry());
                address.setUser(user); 
                return address;
            }).collect(Collectors.toList());

        user.setAddressModel(addresses);
        return user;
    }
}
