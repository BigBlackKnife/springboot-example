package com.blaife.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * lombok测试类
 *  * @Getter get方法（可以在类上或者在属性上）
 *  * @Setter set方法（可以在类上或者在属性上）
 *  * @NoArgsConstructor 生成空参构造器
 *  * @AllArgsConstructor 生成全部参数构造器
 *  * @RequiredArgsConstructor 将标记为@NoNull的属性生成一个构造器
 *  * @ToString 生成所有属性的toString()方法
 *  * @EqualsAndHashCode 生成equals()方法和hashCode方法
 *  * @Data = @Setter+@Getter+@EqualsAndHashCode+@NoArgsConstructor
 *  * @Builder 构造Builder模式的结构。通过内部类Builder()进行构建对象。
 *  * @Value 与@Data相对应的@Value， 两个annotation的主要区别就是如果变量不加@NonFinal ，@Value会给所有的弄成final的。当然如果是final的话，就没有set方法了。
 *  * @Synchronized 同步方法
 *  * @Cleanup @SneakyThrows 自动调用close方法关闭资源。
 */

@Data
public class LomBokTest {
    private String name;
    private String age;
    private String pwd;
}
