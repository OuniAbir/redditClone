package com.programming.redditClone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class VerificationToken {
//    whenever a user is registered, we generated a token in DB, n send this token as part as activation link to the user
//    and when the user clicks that link, we look up the user associated with that token and we enable that user

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Instant expiryDate ;
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;


}
