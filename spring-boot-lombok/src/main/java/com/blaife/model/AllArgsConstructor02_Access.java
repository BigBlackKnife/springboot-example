package com.blaife.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED, staticName = "with")
public class AllArgsConstructor02_Access {
    private static String attribute1;
    private final String attribute2;
    private String attribute3;
}
