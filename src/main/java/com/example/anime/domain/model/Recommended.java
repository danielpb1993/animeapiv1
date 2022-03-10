package com.example.anime.domain.model;
import com.example.anime.domain.model.compositekeys.KeyAnimeUserGenre;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "recommended")
@IdClass(KeyAnimeUserGenre.class)
public class Recommended {

    @Id
    public UUID animeid;
    @Id
    public UUID userid;
    @Id
    public UUID genreid;
}