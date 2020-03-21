package com.blaife.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
public class Data01_Basics {
    private String attribute1;
    @NonNull private String attribute2;
    private final String attribute3 = "";
}
