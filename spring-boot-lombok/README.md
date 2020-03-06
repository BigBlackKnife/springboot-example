# SpringBoot Lombok -- 小辣椒

## 常用的model(实体)的注解
### 1.@Getter/@Setter 生成get/set方法
```java
@Getter
public class Getter_Setter {
    @Setter private String attribute1;
     private String attribute2;
    private String attribute3;
    @Setter private final Integer FINAL_ATTRIBUTE = 01;
}
```
编译后结果为：
```java
public class Getter_Setter {
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private final Integer FINAL_ATTRIBUTE = 1;

    public Getter_Setter() {
    }

    public String getAttribute1() {
        return this.attribute1;
    }

    public String getAttribute2() {
        return this.attribute2;
    }

    public String getAttribute3() {
        return this.attribute3;
    }

    public Integer getFINAL_ATTRIBUTE() {
        return this.FINAL_ATTRIBUTE;
    }

    public void setAttribute1(final String attribute1) {
        this.attribute1 = attribute1;
    }
}
```

- @Getter/@Setter方法可以作用与类上和属性上
- 常量值无法修改，所以无法生成@Setter方法，对FINAL_ATTRIBUTE使用@Setter注解后没有出现编译错误，但是也并没有生成set方法

### 2.@ToString 生成toString方法
@ToString中存在以下可选条件
#### 2.1 `includeFieldNames`
默认为true，为false时生成的toString方法不包括属性名
```java
@ToString(includeFieldNames = false)
public class ToString01_IncludeFieldName {
    private String attribute1;
    private String attribute2;
    private String attribute3;
}
```
编译后结果为：
```java
public class ToString01_IncludeFieldName {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    public ToString_IncludeFieldName() {
    }

    public String toString() {
        return "ToString_IncludeFieldName(" + this.attribute1 + ", " + this.attribute2 + ", " + this.attribute3 + ")";
    }
}
```
若includeFieldNames为true，则toString方法为：  
```java
return "ToString_IncludeFieldName(attribute1=" + this.attribute1 + ", attribute2=" + this.attribute2 + ", attribute3=" + this.attribute3 + ")";
```

#### 2.2 `exclude`
生成toString方法时排除某些属性。
```java
@ToString(exclude = {"attribute1", "a213"})
public class ToString02_Exclude {
    private String attribute1;
    private String attribute2;
    private String attribute3;
}
```
编译后结果为：
```java
public class ToString02_Exclude {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    public ToString02_Exclude() {
    }

    public String toString() {
        return "ToString02_Exclude(attribute2=" + this.attribute2 + ", attribute3=" + this.attribute3 + ")";
    }
}
```
- exclude包含的属性若并不存在与实体类中，编译时并不会出现错误。

#### 2.3 `of`
生成的toString只包含某些属性。
```java
@ToString(of = {"attribute1", "a231"})
public class ToString03_Of {
    private String attribute1;
    private String attribute2;
    private String attribute3;
}
```
编译后结果为：
```java
public class ToString03_Of {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    public ToString03_Of() {
    }

    public String toString() {
        return "ToString03_Of(attribute1=" + this.attribute1 + ")";
    }
}
```
- of包含的属性若并不存在与实体类中，编译时并不会出现错误。

#### 2.4 callSuper
默认值为false，为true时则生成的toString方法里包含父类的toString方法。
```java
@ToString(callSuper = true)
public class ToString04_CallSuper {
    private String attribute1;
    private String attribute2;
    private String attribute3;
}
```
编译后结果为：
```java
public class ToString04_CallSuper {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    public ToString04_CallSuper() {
    }

    public String toString() {
        return "ToString04_CallSuper(super=" + super.toString() + ", attribute1=" + this.attribute1 + ", attribute2=" + this.attribute2 + ", attribute3=" + this.attribute3 + ")";
    }
}
```
- toString方法中第一个属性为super的toString方法

#### 2.5 `doNotUseGetters`
默认值为false，属性的get方法存在时则使用，不存在时则直接使用属性值，为true时指定生成toString时不使用get方法。
```java
@ToString(doNotUseGetters = false)
@Getter
public class ToString05_DoNotUseGetters {
    private String attribute1;
    private String attribute2;
    private String attribute3;
}
```
编译后结果为：
```java
public class ToString05_DoNotUseGetters {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    public ToString05_DoNotUseGetters() {
    }

    public String toString() {
        return "ToString05_DoNotUseGetters(attribute1=" + this.getAttribute1() + ", attribute2=" + this.getAttribute2() + ", attribute3=" + this.getAttribute3() + ")";
    }

    public String getAttribute1() {
        return this.attribute1;
    }

    public String getAttribute2() {
        return this.attribute2;
    }

    public String getAttribute3() {
        return this.attribute3;
    }
}
```
- doNotUseGetters为false时，且存在get方法，toString方法中使用的是`this.getAttribute1`, 为true或不存在get方法时使用`this.attribute1`。

#### 2.6 `onlyExplicitlyIncluded`
默认值为false，正常情况下会包括所有的非静态(non-static)属性，为true时只包含使用@ToString.Include注解标记的属性和方法。
```java
@ToString(onlyExplicitlyIncluded = true)
public class ToString06_OnlyExplicitlyIncluded {
    private String attribute1;
    private String attribute2;
    @ToString.Include
    private String attribute3;
}
```
编译后结果为：
```java
public class ToString06_OnlyExplicitlyIncluded {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    public ToString06_OnlyExplicitlyIncluded() {
    }

    public String toString() {
        return "ToString06_OnlyExplicitlyIncluded(attribute3=" + this.attribute3 + ")";
    }
}
```

#### 2.7 @ToString.Exclude
在属性上使用，指定排除该属性。
```java
@ToString
public class ToString07_Exclude {
    private String attribute1;
    private String attribute2;
    @ToString.Exclude
    private String attribute3;
}
```
编译后结果为：
```java
public class ToString07_Exclude {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    public ToString07_Exclude() {
    }

    public String toString() {
        return "ToString07_Exclude(attribute1=" + this.attribute1 + ", attribute2=" + this.attribute2 + ")";
    }
}
```
- 此注解不能配合exclude和of一起使用

#### 2.8 @ToString.Include
```java
@ToString
public class ToString08_Include {
    private String attribute1;
    private String attribute2;
    @ToString.Include
    private String attribute3;

    @ToString.Include
    private int getInt() {
        return 1;
    }
}
```
编译后结果为：
```java
public class ToString08_Include {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    public ToString08_Include() {
    }

    private int getInt() {
        return 1;
    }

    public String toString() {
        return "ToString08_Include(attribute1=" + this.attribute1 + ", attribute2=" + this.attribute2 + ", attribute3=" + this.attribute3 + ", getInt=" + this.getInt() + ")";
    }
}
```
- toString方法默认是包含属性的，所以在属性上使用时配合onlyExplicitlyIncluded才有意义。
- toString方法默认是不包含方法的，在方法上使用时toString方法内部调用方法实现。

## 3.@EqualsAndHashcode
## 4.@AllArgsContructor
## 5.@NoArgsContructor
## 6.@RequiredArgsContructor
## 7.@Data
## 8.@value
## 9.@Builder
## 10.@Singular
## 11.@Accessors
## 12.@Wither
## 13.@NonNull
## 14.@Log
## 15.@ClearUp
## 16.@Synchronized
## 17.@SneakyThrows
## 18.@Delegate
## 19.var/val