package com.blaife.other;

import lombok.Synchronized;

public class Synchronized01_Basics {
    @Synchronized
    public void test(){
        System.out.println("test @Synchronized");
    }
}
