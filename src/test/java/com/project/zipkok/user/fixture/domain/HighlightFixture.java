package com.project.zipkok.user.fixture.domain;

import com.project.zipkok.model.Highlight;
import com.project.zipkok.model.User;

import java.util.Set;

public class HighlightFixture {

    public static Set<Highlight> createDefaultHighlights(User user) {
        return Highlight.makeDefaultHighlights(user);
    }

}
