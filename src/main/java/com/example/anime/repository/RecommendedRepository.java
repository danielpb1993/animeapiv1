package com.example.anime.repository;


import com.example.anime.domain.model.Recommended;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecommendedRepository extends JpaRepository<Recommended, UUID> {
}