package com.blaife.model;

import lombok.ToString;

@ToString()
public class ToString07_Exclude {
    private String attribute1;
    private String attribute2;
    @ToString.Exclude
    private String attribute3;
}
