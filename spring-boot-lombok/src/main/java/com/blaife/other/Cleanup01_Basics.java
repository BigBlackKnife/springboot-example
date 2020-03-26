package com.blaife.other;

import lombok.Cleanup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Cleanup01_Basics {
    public static void main(String[] args) {
        try {
            @Cleanup FileInputStream fis = new FileInputStream(new File("a.txt"));
            @Cleanup FileOutputStream fos = new FileOutputStream("b.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
