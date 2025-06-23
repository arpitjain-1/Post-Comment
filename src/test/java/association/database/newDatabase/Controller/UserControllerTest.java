package association.database.newDatabase.Controller;

import association.database.newDatabase.DTO.Request.AddressDTO;
import association.database.newDatabase.DTO.Request.UserCreateDTO;
import association.database.newDatabase.DTO.Response.AddressResponseDTO;
import association.database.newDatabase.DTO.Response.UserResponseDTO;
import association.database.newDatabase.Entity.AddressModel;
import association.database.newDatabase.Entity.UserModel;
import association.database.newDatabase.Repository.UserRepository;
import association.database.newDatabase.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;  

    @MockBean
    private UserModel userModel;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        // Create input DTO
        // Step- 1
        UserCreateDTO userDto = new UserCreateDTO();
        userDto.setName("Arpit");
        userDto.setEmail("arpitjain@gmail.com");
        userDto.setPassword("Spring@2025");

        AddressDTO address1 = new AddressDTO();
        address1.setStreet(1);
        address1.setCity("Delhi");
        address1.setCountry("India");

        AddressDTO address2 = new AddressDTO();
        address2.setStreet(2);
        address2.setCity("Indore");
        address2.setCountry("India");

        userDto.setAddressModel(List.of(address1, address2));

        // Step- 2
        when(userService.createUser(any(UserCreateDTO.class))).thenReturn("User Created");

        // Step- 3
        mockMvc.perform(post("/create-new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
            .andExpect(status().isOk())
            .andExpect(content().string("User Created"));
    }

    @Test
    void shouldReturnUserSuccessfully() throws Exception{
        UserResponseDTO UserDTO = new UserResponseDTO();
        UserDTO.setEmail("arpit@gmail.com");
        UserDTO.setName("Arpit");

        AddressResponseDTO address1 = new AddressResponseDTO();
        address1.setCity("Delhi");
        address1.setCountry("India");

        AddressResponseDTO address2 = new AddressResponseDTO();
        address2.setCity("Indore");
        address2.setCountry("India");

        UserDTO.setAddressModel(List.of(address1, address2));

        when(userService.currentUser(1)).thenReturn(UserDTO);
        
        mockMvc.perform(get("/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Arpit"))
            .andExpect(jsonPath("$.email").value("arpit@gmail.com"))
            .andExpect(jsonPath("$.addressModel[0].city").value("Delhi"))
            .andExpect(jsonPath("$.addressModel[0].country").value("India"))
            .andExpect(jsonPath("$.addressModel[1].city").value("Indore"))
            .andExpect(jsonPath("$.addressModel[1].country").value("India"));
    }

    @Test
    void shouldUpdateUserById() throws Exception{
        UserCreateDTO userDto = new UserCreateDTO();
        userDto.setName("Arpit");
        userDto.setEmail("arpitjain@gmail.com");

        UserResponseDTO UserResponse = new UserResponseDTO();
        UserResponse.setName(userDto.getName());
        UserResponse.setEmail(userDto.getEmail());

        AddressResponseDTO address1 = new AddressResponseDTO();
        address1.setCity("Delhi");
        address1.setCountry("India");

        AddressResponseDTO address2 = new AddressResponseDTO();
        address2.setCity("Indore");
        address2.setCountry("India");

        UserResponse.setAddressModel(List.of(address1, address2));

        when(userService.updateUser(1, userDto)).thenReturn(UserResponse);

        mockMvc.perform(put("/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userModel)))
            .andExpect(status().isOk());

    }

    @Test
    void shouldDeleteUserById() throws Exception{
        userModel.setEmail("arpit@gmail.com");
        userModel.setPassword("spring@2025");
        userModel.setName("Arpit");
        userModel.setId(1);
        
        AddressModel address1 = new AddressModel();
        address1.setStreet(1);
        address1.setCity("Delhi");
        address1.setCountry("India");

        AddressModel address2 = new AddressModel();
        address2.setStreet(2);
        address2.setCity("Indore");
        address2.setCountry("India");

        userModel.setAddressModel(List.of(address1, address2));

        when(userService.deleteAccount(1)).thenReturn("User Deleted");
        mockMvc.perform(delete("/delete/1"))
            .andExpect(status().isOk());
    }
}
