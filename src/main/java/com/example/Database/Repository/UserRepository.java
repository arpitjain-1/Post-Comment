package com.example.Database.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Database.Model.UserModel;

@Repository
public interface UserRepository extends JpaRepository <UserModel, Integer>{
    
}
