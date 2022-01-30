package com.programming.redditClone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Cannot be empty")
    private String postName;
    @Nullable
    @Lob
    private String description;
    @Nullable
    private String url;
    private Integer voteCount;
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)  // ManyToOne uni
    private User user ;

    @ManyToOne(fetch = FetchType.LAZY)  // ManyToOne Bi
    private Subreddit subreddit;

}
