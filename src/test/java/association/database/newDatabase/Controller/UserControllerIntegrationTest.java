package association.database.newDatabase.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import association.database.newDatabase.DTO.Request.UserCreateDTO;
import association.database.newDatabase.Entity.UserModel;
import association.database.newDatabase.Repository.AddressRepository;
import association.database.newDatabase.Repository.UserRepository;
import association.database.newDatabase.Utils.TestDataFactory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void createUser() throws Exception {
        UserCreateDTO newUser = TestDataFactory.createValidUserDTO();

        mockMvc.perform(post("/create-new")
                .contentType(MediaType.APPLICATION_JSON) // Set the header
                .content(objectMapper.writeValueAsString(newUser))) // convert java object into a JSON string
                .andExpect(status().isOk());
    }

    @Test
    void returnUser() throws Exception {
        UserCreateDTO newUser = TestDataFactory.createValidUserDTO();
        UserModel savedUser = userRepository.save(TestDataFactory.mapToUserEntity(newUser));

        mockMvc.perform(get("/{id}", savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        UserCreateDTO newUser = TestDataFactory.createValidUserDTO();
        UserModel savedUser = userRepository.save(TestDataFactory.mapToUserEntity(newUser));

        mockMvc.perform(delete("/delete/{id}", savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception{        
        UserCreateDTO newUser = TestDataFactory.createValidUserDTO();
        UserModel savedUser = userRepository.save(TestDataFactory.mapToUserEntity(newUser));
        UserCreateDTO updateUser = TestDataFactory.createUpdateUserPayload();

        mockMvc.perform(put("/update/{id}", savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON) // Set the header
                .content(objectMapper.writeValueAsString(updateUser))) // convert java object into a JSON string
                .andExpect(status().isOk());
    }
}