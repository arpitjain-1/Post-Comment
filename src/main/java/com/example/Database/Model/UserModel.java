package com.example.Database.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name= "usertable", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })

public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    private int id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // @OneToMany -> Indicates a one-to-many relationship (1 User → Many Posts).

    // mappedBy = "user" -> Makes the relationship bidirectional. 
    // Means the 'user' field in PostModel owns the foreign key (User is the inverse side).

    // cascade = CascadeType.ALL -> All operations (persist, merge, remove, refresh, detach) on User 
    // will also apply to associated Posts.

    // orphanRemoval = true -> If a Post is removed from the User's posts list 
    // and is not referenced elsewhere, it will be deleted from the database.


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostModel> posts;


    UserModel(){}

    UserModel(int id, String name, String email){
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
