package com.programming.redditClone.repository;

import com.programming.redditClone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit,Long> {
   Optional<Subreddit> findByName(String subredditName);
}
