package com.blaife;

import com.blaife.model.LomBokTest;

/**
 * 引入lonbokd的依赖，在实体类中使用lombok的注解进行处理
 */
public class LombokTest {
    public static void main(String[] args) {
        LomBokTest l = new LomBokTest();
        l.setName("test");
        l.setAge("12");
        l.setPwd("fs");
        System.out.println(l);
    }
}
