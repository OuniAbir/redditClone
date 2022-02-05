package com.programming.redditClone.service;

import com.programming.redditClone.dto.SubredditDto;
import com.programming.redditClone.exception.SpringRedditCloneException;
import com.programming.redditClone.mapper.SubredditMapper;
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
    final private SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto dto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(dto));
        dto.setId(save.getId());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll().stream().map(this.subredditMapper::mapSubredditToDto).collect(Collectors.toList());
    }

    public SubredditDto getSubredditById(long id) {
        Subreddit entity = subredditRepository.findById(id).orElseThrow(()-> new SpringRedditCloneException("Don't find subreddit with id "+ id));
        return subredditMapper.mapSubredditToDto(entity);
    }
}
