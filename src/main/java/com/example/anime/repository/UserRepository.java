package com.example.anime.repository;

import com.example.anime.domain.model.User;
import com.example.anime.domain.model.projections.ProjectionUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
    List<ProjectionUser>findBy();
    <T>List<T> findByUserid(UUID id, Class<T> type);
}