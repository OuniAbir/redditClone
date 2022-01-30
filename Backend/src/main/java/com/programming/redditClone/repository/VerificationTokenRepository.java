package com.programming.redditClone.repository;

import com.programming.redditClone.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository  extends JpaRepository<VerificationToken,Long> {
}
