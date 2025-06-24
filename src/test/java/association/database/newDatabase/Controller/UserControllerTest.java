package association.database.newDatabase.Controller;

import association.database.newDatabase.DTO.Request.AddressDTO;
import association.database.newDatabase.DTO.Request.UserCreateDTO;
import association.database.newDatabase.DTO.Response.AddressResponseDTO;
import association.database.newDatabase.DTO.Response.UserResponseDTO;
import association.database.newDatabase.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

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

// @WebMvcTest:- Used when you wanna test a specific controller in unit testing
// @MockMvc:- Creates the fake client for HTTP Request
// @MockBean:- Mock the Service and Repository
// @Test:- Specify the current method is Test method

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

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
    void shouldReturnUserSuccessfully() throws Exception {
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
    void shouldUpdateUserById() throws Exception {

        UserCreateDTO updateRequest = new UserCreateDTO();
        updateRequest.setName("Arpit");
        updateRequest.setEmail("arpitjain@gmail.com");

        UserResponseDTO mockResponse = new UserResponseDTO();
        mockResponse.setName("Arpit");
        mockResponse.setEmail("arpitjain@gmail.com");

        when(userService.updateUser(eq(1), any(UserCreateDTO.class)))
                .thenReturn(mockResponse);


        mockMvc.perform(put("/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(jsonPath("$.name").value("Arpit"))
                .andExpect(jsonPath("$.email").value("arpitjain@gmail.com"));
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        when(userService.deleteAccount(1)).thenReturn("User Deleted");
        mockMvc.perform(delete("/delete/1"))
                .andExpect(status().isOk());
    }
}
