package com.blaife.other;

import lombok.NonNull;

public class NonNull01_Basics {

    private String name;

    public static void getName(@NonNull NonNull01_Basics n) {
        System.out.println(n.name);
    }

    public static void main(String[] args) {
        NonNull01_Basics.getName(null);
    }
}
