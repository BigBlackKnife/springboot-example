package com.blaife.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "with")
public class AllArgsConstructor01_StaticName {
    private static String attribute1;
    private final String attribute2;
    private String attribute3;
}
