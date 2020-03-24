package com.blaife.model;

import lombok.Builder;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Builder
public class Builder02_Singular {
    private String attribute1;
    private String attribute2;
    @Singular(value = "attribute3")
    private List<String> attribute3;
}
