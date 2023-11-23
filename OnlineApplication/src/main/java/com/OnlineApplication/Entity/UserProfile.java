package com.OnlineApplication.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String bio;

    @Lob
    private String profilePicture;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public UserProfile( String name, String bio) {

        this.name = name;
        this.bio = bio;
    }

    public UserProfile() {

    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setName(String name) {
        this.name = name;
    }


    public UserProfile( String name, String bio, String profilePicture,List<Post> posts) {
        this.posts = posts;
        this.name = name;
        this.bio = bio;
        this.profilePicture = profilePicture;
    }
}
