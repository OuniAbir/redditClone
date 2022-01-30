package com.programming.redditClone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Instant createdDate;
    @NotBlank
    private String text;

    @ManyToOne(fetch = FetchType.LAZY) //uni many side
    private Post post ;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


}
