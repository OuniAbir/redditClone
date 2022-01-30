package com.programming.redditClone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Description is required")
    private String description ;
    @NotBlank(message = "Community name is required")
    private String name ;
    private Instant createdDate;

    @OneToMany(mappedBy = "subreddit") // OneToMany Bi
    private List<Post> posts;

    @ManyToOne(fetch = FetchType.LAZY) // ManyToOne uni
    private User user;

}
