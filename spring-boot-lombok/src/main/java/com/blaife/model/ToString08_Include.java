package com.blaife.model;

import lombok.ToString;

@ToString
public class ToString08_Include {
    private String attribute1;
    private String attribute2;
    @ToString.Include
    private String attribute3;

    @ToString.Include
    private int getInt() {
        return 1;
    }
}
