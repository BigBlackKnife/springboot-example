package com.blaife.model;

import lombok.ToString;

@ToString(exclude = {"attribute1", "a213"})
public class ToString02_Exclude {
    private String attribute1;
    private String attribute2;
    private String attribute3;
}
