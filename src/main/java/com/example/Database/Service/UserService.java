package com.example.Database.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.Database.Model.UserModel;
import com.example.Database.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public UserModel createUser(UserModel user) {
        return repo.save(user);
    }

    public Optional<UserModel> currentUser(int id) {
        return repo.findById(id);
    }

    public Optional<UserModel> updateUser(int id, UserModel user) {
        if (repo.existsById(id)) {
            user.setId(id); 
            return Optional.of(repo.save(user));
        } else {
            return Optional.empty(); 
        }
    }

    public boolean deleteUser(int id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
