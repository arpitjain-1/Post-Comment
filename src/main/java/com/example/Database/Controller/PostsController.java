package com.example.Database.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.Database.Model.PostModel;
import com.example.Database.Service.PostService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
public class PostsController {

    @Autowired
    private PostService postService;

    @GetMapping("/all")
    public List<PostModel> showAllPost() {
        return postService.showAllPost();
    }

    @PostMapping("/new")
    public PostModel createNewPost(@RequestBody PostModel post) {
        return postService.createNewPost(post);
    }

    @GetMapping("/post/{id}")
    public Optional<PostModel> showPost(@PathVariable int id) {
        return postService.showPost(id);
    }

    @DeleteMapping("/post/{id}")
    public boolean deletePost(@PathVariable int id){
        return postService.deletePost(id);
    }

    @PutMapping("/post/update/{id}")
    public Optional<PostModel> updatePost(@PathVariable int id, @RequestBody PostModel post) {
        return postService.updatePost(id, post);
    }
}