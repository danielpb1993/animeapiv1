package com.example.anime.domain.model.projections;

import java.util.UUID;

public interface ProjectionAnimeFavorited {
    UUID getAnimeid();
    String getName();
    String getImageurl();
}
