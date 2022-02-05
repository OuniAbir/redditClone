package com.programming.redditClone.repository;

import com.programming.redditClone.model.Post;
import com.programming.redditClone.model.Subreddit;
import com.programming.redditClone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
