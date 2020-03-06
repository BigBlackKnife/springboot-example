package com.blaife.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Getter_Setter {
    @Setter private String attribute1;
     private String attribute2;
    private String attribute3;
    @Setter private final Integer FINAL_ATTRIBUTE = 01;
}
