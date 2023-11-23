package com.OnlineApplication.Controller;

import com.OnlineApplication.Entity.Post;
import com.OnlineApplication.Entity.UserProfile;
import com.OnlineApplication.Repository.PostRepo;
import com.OnlineApplication.Repository.userRepo;
import com.OnlineApplication.payload.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private userRepo UserRepo;

    @PostMapping("/{userId}")
    public ResponseEntity<PostDto> CreatePost(@PathVariable long userId, @RequestBody PostDto postDto){
        UserProfile userProfile = UserRepo.findById(userId).get();
        Post post = mapToPost(postDto);
        post.setUserProfile(userProfile);

        Post save = postRepo.save(post);
        PostDto postDto1 = mapToDto(save);
        return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }



    Post mapToPost(PostDto dto){
        Post post = new Post();
        post.setContent(dto.getContent());
        post.setName(dto.getName());

        return post;
    }

    PostDto mapToDto(Post post){
        PostDto dto = new PostDto();
        dto.setContent(post.getContent());
        dto.setName(post.getName());

        return dto;
    }


}
