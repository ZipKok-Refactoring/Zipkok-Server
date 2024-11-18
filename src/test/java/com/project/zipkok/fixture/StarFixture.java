package com.project.zipkok.fixture;

import com.project.zipkok.model.Kok;
import com.project.zipkok.model.Star;

public class StarFixture {

    public static Star createStar(Kok kok) {
        return Star.builder()
                .facilityStar(5)
                .infraStar(5)
                .structureStar(5)
                .vibeStar(5)
                .kok(kok)
                .build();
    }
}
