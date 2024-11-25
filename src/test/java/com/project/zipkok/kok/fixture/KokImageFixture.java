package com.project.zipkok.kok.fixture;

import com.project.zipkok.model.KokImage;
import org.springframework.test.util.ReflectionTestUtils;

public class KokImageFixture {
    public static final KokImage DUMMY_KOK_IMAGE = new KokImage("https://test.com", "OUTER");

    static{
        ReflectionTestUtils.setField(DUMMY_KOK_IMAGE, "kokImageId", 1L);
    }
}
