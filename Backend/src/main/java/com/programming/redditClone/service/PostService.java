package com.programming.redditClone.service;

import com.programming.redditClone.dto.PostRequest;
import com.programming.redditClone.dto.PostResponse;
import com.programming.redditClone.exception.SpringRedditCloneException;
import com.programming.redditClone.mapper.PostMapper;
import com.programming.redditClone.model.Post;
import com.programming.redditClone.model.Subreddit;
import com.programming.redditClone.model.User;
import com.programming.redditClone.repository.PostRepository;
import com.programming.redditClone.repository.SubredditRepository;
import com.programming.redditClone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    final private UserRepository userRepository;
    private final PostMapper postMapper;
    private final AuthService authService;

    @Transactional
    public Post save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() -> new SpringRedditCloneException("Subreddit with this name not found" + postRequest.getSubredditName()));

        Post post= postRepository.save(postMapper.mapToPost(postRequest, subreddit, authService.getCurrentUser()));
        return post;
    }
    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new SpringRedditCloneException("couldn't fid post with id " + id));
        return postMapper.mapToDto(post) ;
    }
    @Transactional(readOnly = true)
    public List<PostResponse> listAllPosts() {
        return postRepository.findAll().stream().map(this.postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostBySubreddit(long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(()-> new SpringRedditCloneException("couldn't find subbredit with "+id));
        List<Post> posts =  postRepository.findBySubreddit(subreddit);
        return posts.stream().map(this.postMapper::mapToDto).collect(Collectors.toList());
    }

    public List<PostResponse> getPostByUser(long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new SpringRedditCloneException("coudn't find user with id "+ id));
        List<Post> posts = postRepository.findByUser(user);
        return posts.stream().map(this.postMapper::mapToDto).collect(Collectors.toList());

    }
}
