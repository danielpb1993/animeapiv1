package com.example.anime.domain.model;

import com.example.anime.domain.model.compositekeys.KeyRating;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "rating")
@IdClass(KeyRating.class)
public class Rating {

    @Id
    public String username;
    @Id
    public String anime;
    public double score;



}