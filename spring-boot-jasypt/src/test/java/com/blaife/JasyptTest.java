package com.blaife;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Blaife
 * @description jasypt 测试类
 * @data 2020/5/1 22:56
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class JasyptTest {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void encryptPwd() {
        String plaintext = "blaife";
        String ciphertext = stringEncryptor.encrypt(plaintext);
        System.out.println("==================");
        System.out.println(plaintext + "加密后为：" + ciphertext);
    }

    @Test
    public void decryptPwd() {
        String ciphertext = "AHpB+YgqiOBInBYSeCCxh87dqCSuapTT5sKgCH/mt7bfKzyJ6/Py1mudFB3qc5Yb";
        String plaintext = stringEncryptor.decrypt(ciphertext);
        System.out.println("==================");
        System.out.println(ciphertext + "解密后为：" + plaintext);
    }

}
