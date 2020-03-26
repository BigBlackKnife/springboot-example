package com.blaife.other;

import lombok.Synchronized;

public class Synchronized02_name {
    String name = "";
    @Synchronized("name")
    public void test(){
        System.out.println("test @Synchronized");
    }
}
