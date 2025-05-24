package com.example.Database.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.Database.Model.UserModel;
import com.example.Database.Service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class UserController {

    @Autowired
    UserService Service;

    @PostMapping("/newUser")
    public UserModel newUser(@RequestBody UserModel user) {
        return Service.createUser(user);
    }

    @GetMapping("/{id}")
    public Optional<UserModel> currentUser(@PathVariable int id) {
        return Service.currentUser(id);
    }

    @PutMapping("update/{id}")
    public Optional<UserModel> updateUser(@PathVariable int id, @RequestBody UserModel user) {
        return Service.updateUser(id, user);
    }

    @DeleteMapping("delete/{id}")
    public boolean deleteUser(@RequestParam int id){
        return Service.deleteUser(id);
    }    
}