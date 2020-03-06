package com.blaife.model;

import lombok.ToString;

@ToString(onlyExplicitlyIncluded = true)
public class ToString06_OnlyExplicitlyIncluded {
    private String attribute1;
    private String attribute2;
    @ToString.Include
    private String attribute3;
}
