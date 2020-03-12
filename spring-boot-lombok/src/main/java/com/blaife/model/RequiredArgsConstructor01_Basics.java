package com.blaife.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequiredArgsConstructor01_Basics {
    private static String attribute1;
    private final String attribute2;
    @NonNull
    private String attribute3;
}
