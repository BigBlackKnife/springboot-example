package com.blaife.other;

import lombok.SneakyThrows;

import java.io.FileReader;

public class SneakyThrows01_Basics {
    @SneakyThrows
    public static void main(String[] args) {
        FileReader fileReader = new FileReader("a.txt");
    }
}
