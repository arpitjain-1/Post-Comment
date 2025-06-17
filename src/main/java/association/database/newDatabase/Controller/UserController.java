package association.database.newDatabase.Controller;

import org.springframework.web.bind.annotation.RestController;

import association.database.newDatabase.DTO.Request.UserCreateDTO;
import association.database.newDatabase.DTO.Response.UserResponseDTO;
import association.database.newDatabase.Entity.IdCardModel;
import association.database.newDatabase.Entity.UserModel;
import association.database.newDatabase.Service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    UserService userService;

    @PostMapping("/create-new")
    public String handleNewUserCreation(@RequestBody UserCreateDTO userdata) {
        return userService.createUser(userdata);
    }

    @PutMapping("update/{id}")
    public UserModel handleUserUpdate(@PathVariable int id, @RequestBody UserModel userData) {
        return userService.updateUser(id, userData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> handleUserProfile(@PathVariable int id) {
        UserResponseDTO dto = userService.currentUser(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/print-all")
    public List<UserModel> printAllUsers() {
        return userService.printAllUsers();
    }

    @DeleteMapping("delete/id")
    public String deleteUserAccount(@PathVariable int id){
        userService.deleteAccount(id);
        return "User Deleted";
    }    
    
}
