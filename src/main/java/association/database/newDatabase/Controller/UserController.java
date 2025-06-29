package association.database.newDatabase.Controller;

import org.springframework.web.bind.annotation.RestController;
import association.database.newDatabase.DTO.Request.UserCreateDTO;
import association.database.newDatabase.DTO.Response.UserResponseDTO;
import association.database.newDatabase.Service.UserService;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private Environment environment;

    @PostMapping("/create-new")
    public ResponseEntity<String> handleNewUserCreation(@RequestBody UserCreateDTO userdata) {
        return ResponseEntity.ok(userService.createUser(userdata));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> handleUserUpdate(@PathVariable int id, @RequestBody UserCreateDTO userData) {
        return ResponseEntity.ok(userService.updateUser(id, userData));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> handleUserProfile(@PathVariable int id) {
        UserResponseDTO dto = userService.currentUser(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/print-all")
    public ResponseEntity<?> printAllUsers() {
        if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("This endpoint is not available in test profile");
        }
        return ResponseEntity.ok(userService.printAllUsers());
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUserAccount(@PathVariable int id) {
        userService.deleteAccount(id);
        return "User Deleted";
    }
    
    @GetMapping("/check-profile")
    public String checkProfile() {
        return "Active profiles: " + String.join(", ", environment.getActiveProfiles());
    }
}