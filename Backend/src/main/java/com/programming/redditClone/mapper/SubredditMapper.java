package com.programming.redditClone.mapper;

import com.programming.redditClone.dto.SubredditDto;
import com.programming.redditClone.model.Post;
import com.programming.redditClone.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface SubredditMapper {
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default int mapPosts(List<Post> posts) {
        return posts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto dto);
}
