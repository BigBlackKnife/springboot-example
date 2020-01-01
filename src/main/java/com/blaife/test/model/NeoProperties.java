package com.blaife.test.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NeoProperties {

    @Value("${com.blaife.title}")
    private String title;
    @Value("${com.blaife.description}")
    private String description;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
