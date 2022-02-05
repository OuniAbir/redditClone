package com.programming.redditClone.controller;

import com.programming.redditClone.dto.PostRequest;
import com.programming.redditClone.dto.PostResponse;
import com.programming.redditClone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @PostMapping
    public ResponseEntity createPost(
            @RequestBody PostRequest postRequest
            ){
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> listAllPosts()
    {
        return ResponseEntity.status(HttpStatus.OK).body(postService.listAllPosts());
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostBySubreddit(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostBySubreddit(id));
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<PostResponse>> getPostByUser(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostByUser(id));
    }
}
