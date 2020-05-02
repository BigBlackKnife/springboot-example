# Jasypt

## Maven依赖
```xml
<dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>3.0.2</version>
</dependency>
```

## 加密测试类

配置文件中示例如下：
```
jasypt.encryptor.password=blaife
# 不指定前缀后缀, 默认为ENC();
jasypt.encryptor.property.prefix=blaife(
jasypt.encryptor.property.suffix=)

# mySql 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/shirotest?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
spring.datasource.username=root
spring.datasource.password=blaife
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

测试类代码如下：
```java
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
```
得到类似如下的信息
```
blaife加密后为：2KkLpkHLXIGZHkN6vZNgwQDSNIPO/98XIwMLw60IBjB/nHROItEMb2v5wvpq/V5v
```
```
AHpB+YgqiOBInBYSeCCxh87dqCSuapTT5sKgCH/mt7bfKzyJ6/Py1mudFB3qc5Yb解密后为：blaife
```

将数据库的密码修改
```
spring.datasource.password=blaife(AHpB+YgqiOBInBYSeCCxh87dqCSuapTT5sKgCH/mt7bfKzyJ6/Py1mudFB3qc5Yb)
```

- 每一次执行加密过程所得的密文都会不同
- 只能解密对应Jasypt盐值的密文，其他盐值的密文解密会出现`EncryptionOperationNotPossibleException`错误

## 使其更安全

### 自定义加密器
```
public class BlaifeEncryptorCfg {

    @Bean(name = "blaifeEncryptor")
    public StringEncryptor blaifeStringEncryptor() {

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("CodeSheep");
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        return encryptor;
    }
}
```

### 加密密钥不要写在配置文件中

##### 1. 作为程序启动时的命令行参数来带入

```
java -jar yourproject.jar --jasypt.encryptor.password=CodeSheep
```


##### 2. 作为程序启动时的应用环境变量来带入

```
java -Djasypt.encryptor.password=CodeSheep -jar yourproject.jar
```

##### 3. 作为系统环境变量的方式来带入

```
jasypt.encryptor.password=${JASYPT_ENCRYPTOR_PASSWORD:}
```