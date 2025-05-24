package com.example.Database.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Database.Model.PostModel;
import com.example.Database.Model.UserModel;
import com.example.Database.Repository.PostRepository;
import com.example.Database.Repository.UserRepository;


// public class PostService {
    
//    @Autowired
//    private PostRepository postRepo;
  
//    @Autowired
//    private UserRepository userRepo;

//    public PostModel showAllPost(Object object){
//     return PostModel.all();
//    }

//    public PostModel createNewPost(PostModel post){
//     Optional<UserModel> user = userRepo.findById(post.getUser_id());
//     if (user.isPresent()) {
//         post.setUser(user.get());
//         return postRepo.save(post);
//     } else {
//         throw new RuntimeException("User not found");
//     }
//    }

//    public Optional<UserModel> showPost(int id){
//     return userRepo.findById(id);
//    }

//    public Optional<PostModel> updatePost(int id, PostModel postModel){
//     if(postRepo.existsById(id)){
//         postModel.setId(id);
//         return Optional.of(postRepo.save(postModel));
//     }else{
//         return null;
//     }
//    }

//    public boolean deletePost(int id){
//     if (postRepo.existsById(id)) {
//             postRepo.deleteById(id);
//             return true;
//         } else {
//             return false;
//         }
//    }
// }
@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

    public PostModel createNewPost(PostModel post){
        Optional<UserModel> user = userRepo.findById(post.getUser_id());
        if (user.isPresent()) {
            post.setUser(user.get());
            return postRepo.save(post);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Optional<PostModel> showPost(int id){
        return postRepo.findById(id);
    }

    public java.util.List<PostModel> showAllPost(){
        return postRepo.findAll();
    }

    public Optional<PostModel> updatePost(int id, PostModel postModel){
        if(postRepo.existsById(id)){
            postModel.setId(id);
            Optional<UserModel> user = userRepo.findById(postModel.getUser_id());
            user.ifPresent(postModel::setUser);
            return Optional.of(postRepo.save(postModel));
        } else {
            return Optional.empty();
        }
    }

    public boolean deletePost(int id){
        if (postRepo.existsById(id)) {
            postRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
