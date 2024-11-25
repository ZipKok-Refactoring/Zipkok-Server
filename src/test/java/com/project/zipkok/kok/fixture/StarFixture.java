package com.project.zipkok.kok.fixture;

import com.project.zipkok.model.Star;
import org.springframework.test.util.ReflectionTestUtils;

public class StarFixture {
    public static final Star DUMMY_STAR = new Star(5,5,5,5,null);

    static {
        ReflectionTestUtils.setField(DUMMY_STAR, "starId", 1L);
    }
}
