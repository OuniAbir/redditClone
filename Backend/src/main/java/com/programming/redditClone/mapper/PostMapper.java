package com.programming.redditClone.mapper;

import com.programming.redditClone.dto.PostRequest;
import com.programming.redditClone.dto.PostResponse;
import com.programming.redditClone.model.Post;
import com.programming.redditClone.model.Subreddit;
import com.programming.redditClone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PostMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "subreddit" , source = "subreddit")
    @Mapping(target = "id", source = "postRequest.postId")
    @Mapping(target = "description", source="postRequest.description")
    Post mapToPost(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "userName", source = "post.user.username")
    @Mapping(target = "subredditName", source = "post.subreddit.name")
    PostResponse mapToDto(Post post);
}
