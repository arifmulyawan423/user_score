package com.msn.user_score.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msn.user_score.entity.UserScore;

public interface UserScoreRepository extends JpaRepository<UserScore, UUID>{

}
