package com.programming.redditClone.service;

import com.programming.redditClone.dto.SubredditDto;
import com.programming.redditClone.model.Subreddit;
import com.programming.redditClone.repository.SubredditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubredditService {
    final private SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto dto) {
        Subreddit save = subredditRepository.save(mapSubreddit(dto));
        dto.setId(save.getId());
        return dto;
    }

    private Subreddit mapSubreddit(SubredditDto dto) {
        return Subreddit.builder().name(dto.getName()).description(dto.getDescription()).build();
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder().id(subreddit.getId())
                .name(subreddit.getName())
                .numberOfPosts(subreddit.getPosts().size())
                .description(subreddit.getDescription())
                .build();
    }

}
