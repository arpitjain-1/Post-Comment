package com.example.Database.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Database.Model.PostModel;

public interface PostRepository extends JpaRepository <PostModel, Integer>{

}
