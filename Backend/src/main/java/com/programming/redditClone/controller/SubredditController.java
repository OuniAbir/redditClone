package com.programming.redditClone.controller;

import com.programming.redditClone.dto.SubredditDto;
import com.programming.redditClone.service.SubredditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subreddit")
public class SubredditController {

    final private SubredditService subredditService ;
    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(
            @RequestBody SubredditDto dto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(dto));
    }
    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAll());
    }
}
