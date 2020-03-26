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
在属性和方法上使用，指定包含该属性和方法。
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
- toString方法默认是不包含方法的，在方法上使用时编译后的toString方法内部调用指定方法进行实现。

### 3.@AllArgsConstructor 生成全属性构造方法
作用于类上，生成包含所有非static修饰的属性的构造方法

#### 3.1 `staticName`
私有化构造方法，并生成一个以staticName的值命名的静态工厂函数.
```java
@AllArgsConstructor(staticName = "with")
public class AllArgsConstructor01_StaticName {
    private static String attribute1;
    private final String attribute2;
    private String attribute3;
}

```
编译后结果为：
```java
public class AllArgsConstructor01_StaticName {
    private static String attribute1;
    private final String attribute2;
    private String attribute3;

    private AllArgsConstructor01_StaticName(final String attribute2, final String attribute3) {
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
    }

    public static AllArgsConstructor01_StaticName with(final String attribute2, final String attribute3) {
        return new AllArgsConstructor01_StaticName(attribute2, attribute3);
    }
}
```

#### 3.2 `access`
指定生成的构造方法的访问级别，默认为public。和staticName同时使用也可以指定静态工厂函数的访问级别。
```java
@AllArgsConstructor(access = AccessLevel.PROTECTED, staticName = "with")
public class AllArgsConstructor02_Access {
    private static String attribute1;
    private final String attribute2;
    private String attribute3;
}
```
编译后结果为：
```java
public class AllArgsConstructor02_Access {
    private static String attribute1;
    private final String attribute2;
    private String attribute3;

    private AllArgsConstructor02_Access(final String attribute2, final String attribute3) {
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
    }

    protected static AllArgsConstructor02_Access with(final String attribute2, final String attribute3) {
        return new AllArgsConstructor02_Access(attribute2, attribute3);
    }
}
```
- 和staticName同时使用时指定的时static方法的修饰符，而非构造方法的修饰符

### 4.@NoArgsConstructor 生成无参构造方法
作用于类上，会生成没有参数的构造方法。

#### 4.1 `force`
如果存在用final修饰的成员变量且未初始化，则需使用@NoArgsConstructor(force=true)，所有的final属性都会被初始化为0、false、null等。
若成员变量使用了@NonNull，那么该注解将不起作用。
```java
@NoArgsConstructor(force = true)
public class NoArgsConstructor01_Force {
    private static String attribute1;
    private final String attribute2;
    private String attribute3;
}
```
编译后结果为：
```java
public class NoArgsConstructor01_Force {
    private static String attribute1;
    private final String attribute2 = null;
    private String attribute3;

    public NoArgsConstructor01_Force() {
    }
}
```
- 如果存在final修饰的成员变量且未初始化，不使用force会提示 `not have been initialized` ;

#### 4.2 `staticName`
参照@AllArgsConstructor注解

#### 4.3 `access`
参照@AllArgsConstructor注解

### 5.@RequiredArgsContructor 指向性加入构造方法
用@NonNull修饰的参数和用final修饰且未初始化的参数才会加入构造方法。

#### 5.1 基本使用
```java
@RequiredArgsConstructor
public class RequiredArgsConstructor01_Basics {
    private static String attribute1;
    private final String attribute2;
    @NonNull
    private String attribute3;
}
```
编译后结果为：
```java
public class RequiredArgsConstructor01_Basics {
    private static String attribute1;
    private final String attribute2;
    @NonNull
    private String attribute3;

    public RequiredArgsConstructor01_Basics(final String attribute2, @NonNull final String attribute3) {
        if (attribute3 == null) {
            throw new NullPointerException("attribute3 is marked non-null but is null");
        } else {
            this.attribute2 = attribute2;
            this.attribute3 = attribute3;
        }
    }
}
```

#### 5.2 `staticName`
参照@AllArgsConstructor注解

#### 5.3 `access`
参照@AllArgsConstructor注解

### 6.@EqualsAndHashcode 生成equals和hashCode方法

#### 6.1 基本使用
```java
@EqualsAndHashCode
public class EqualsAndHashCode01_Basics {
    private String attribute1;
    private String attribute2;
    private String attribute3;
}
```
编译后结果为：
```java
public class EqualsAndHashCode01_Basics {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    public EqualsAndHashCode01_Basics() {
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof EqualsAndHashCode01_Basics)) {
            return false;
        } else {
            EqualsAndHashCode01_Basics other = (EqualsAndHashCode01_Basics)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$attribute1 = this.attribute1;
                    Object other$attribute1 = other.attribute1;
                    if (this$attribute1 == null) {
                        if (other$attribute1 == null) {
                            break label47;
                        }
                    } else if (this$attribute1.equals(other$attribute1)) {
                        break label47;
                    }

                    return false;
                }

                Object this$attribute2 = this.attribute2;
                Object other$attribute2 = other.attribute2;
                if (this$attribute2 == null) {
                    if (other$attribute2 != null) {
                        return false;
                    }
                } else if (!this$attribute2.equals(other$attribute2)) {
                    return false;
                }

                Object this$attribute3 = this.attribute3;
                Object other$attribute3 = other.attribute3;
                if (this$attribute3 == null) {
                    if (other$attribute3 != null) {
                        return false;
                    }
                } else if (!this$attribute3.equals(other$attribute3)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EqualsAndHashCode01_Basics;
    }

    public int hashCode() {
        int PRIME = true;
        int result = 1;
        Object $attribute1 = this.attribute1;
        int result = result * 59 + ($attribute1 == null ? 43 : $attribute1.hashCode());
        Object $attribute2 = this.attribute2;
        result = result * 59 + ($attribute2 == null ? 43 : $attribute2.hashCode());
        Object $attribute3 = this.attribute3;
        result = result * 59 + ($attribute3 == null ? 43 : $attribute3.hashCode());
        return result;
    }
}
```

#### 6.2 `exclude`
参照@ToString注解

#### 6.3 `of`
参照@ToString注解


### 7.@Data 等价于@Getter+@Setter+@ToString+@EqualsAndHashCode+@RequiredArgsConstructor
当有属性使用@NonNull或者final修饰时，生成的代码中没有公共的无参构造方法，有私有的无参构造方法，
相当于上面的一串注解再加上@NoArgsConstructor(access=AccessLevel.PRIVATE)注解

#### 7.1 基本使用
```java
@Data
public class Data01_Basics {
    private String attribute1;
    @NonNull private String attribute2;
    private final String attribute3 = "";
}
```
编译后结果为：
```java
public class Data01_Basics {
    public Data01_Basics() {
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Data01_Basics)) {
            return false;
        } else {
            Data01_Basics other = (Data01_Basics)o;
            return other.canEqual(this);
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Data01_Basics;
    }

    public int hashCode() {
        int result = true;
        return 1;
    }

    public String toString() {
        return "Data01_Basics()";
    }
}
```

#### 7.2 `staticConstructor`
如果指定其值，则生成的构造方法将是私有的，而创建的是可以用来创建实例的静态工厂方法。
```java
@Data(staticConstructor = "of")
public class Data02_StaticConstructor {
    private String attribute1;
    private String attribute2;
    private String attribute3;
}
```
编译后结果为：
```java
public class Data02_StaticConstructor {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    private Data02_StaticConstructor() {
    }

    public static Data02_StaticConstructor of() {
        return new Data02_StaticConstructor();
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

    public void setAttribute1(final String attribute1) {
        this.attribute1 = attribute1;
    }

    public void setAttribute2(final String attribute2) {
        this.attribute2 = attribute2;
    }

    public void setAttribute3(final String attribute3) {
        this.attribute3 = attribute3;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Data02_StaticConstructor)) {
            return false;
        } else {
            Data02_StaticConstructor other = (Data02_StaticConstructor)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$attribute1 = this.getAttribute1();
                    Object other$attribute1 = other.getAttribute1();
                    if (this$attribute1 == null) {
                        if (other$attribute1 == null) {
                            break label47;
                        }
                    } else if (this$attribute1.equals(other$attribute1)) {
                        break label47;
                    }

                    return false;
                }

                Object this$attribute2 = this.getAttribute2();
                Object other$attribute2 = other.getAttribute2();
                if (this$attribute2 == null) {
                    if (other$attribute2 != null) {
                        return false;
                    }
                } else if (!this$attribute2.equals(other$attribute2)) {
                    return false;
                }

                Object this$attribute3 = this.getAttribute3();
                Object other$attribute3 = other.getAttribute3();
                if (this$attribute3 == null) {
                    if (other$attribute3 != null) {
                        return false;
                    }
                } else if (!this$attribute3.equals(other$attribute3)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Data02_StaticConstructor;
    }

    public int hashCode() {
        int PRIME = true;
        int result = 1;
        Object $attribute1 = this.getAttribute1();
        int result = result * 59 + ($attribute1 == null ? 43 : $attribute1.hashCode());
        Object $attribute2 = this.getAttribute2();
        result = result * 59 + ($attribute2 == null ? 43 : $attribute2.hashCode());
        Object $attribute3 = this.getAttribute3();
        result = result * 59 + ($attribute3 == null ? 43 : $attribute3.hashCode());
        return result;
    }

    public String toString() {
        return "Data02_StaticConstructor(attribute1=" + this.getAttribute1() + ", attribute2=" + this.getAttribute2() + ", attribute3=" + this.getAttribute3() + ")";
    }
}
```

### 8.@value 会给所有属性加上final修饰并生成get方法、toString方法和@EqualsAndHashCode注解生成的代码。

#### 8.1 基本使用
```java
@Value
public class Value01_Basics {
    private String attribute1;
    private String attribute2;
    private String attribute3;
}
```
编译后结果为：
```java
public final class Value01_Basics {
    private final String attribute1;
    private final String attribute2;
    private final String attribute3;

    public Value01_Basics(final String attribute1, final String attribute2, final String attribute3) {
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
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

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Value01_Basics)) {
            return false;
        } else {
            Value01_Basics other;
            label44: {
                other = (Value01_Basics)o;
                Object this$attribute1 = this.getAttribute1();
                Object other$attribute1 = other.getAttribute1();
                if (this$attribute1 == null) {
                    if (other$attribute1 == null) {
                        break label44;
                    }
                } else if (this$attribute1.equals(other$attribute1)) {
                    break label44;
                }

                return false;
            }

            Object this$attribute2 = this.getAttribute2();
            Object other$attribute2 = other.getAttribute2();
            if (this$attribute2 == null) {
                if (other$attribute2 != null) {
                    return false;
                }
            } else if (!this$attribute2.equals(other$attribute2)) {
                return false;
            }

            Object this$attribute3 = this.getAttribute3();
            Object other$attribute3 = other.getAttribute3();
            if (this$attribute3 == null) {
                if (other$attribute3 != null) {
                    return false;
                }
            } else if (!this$attribute3.equals(other$attribute3)) {
                return false;
            }

            return true;
        }
    }

    public int hashCode() {
        int PRIME = true;
        int result = 1;
        Object $attribute1 = this.getAttribute1();
        int result = result * 59 + ($attribute1 == null ? 43 : $attribute1.hashCode());
        Object $attribute2 = this.getAttribute2();
        result = result * 59 + ($attribute2 == null ? 43 : $attribute2.hashCode());
        Object $attribute3 = this.getAttribute3();
        result = result * 59 + ($attribute3 == null ? 43 : $attribute3.hashCode());
        return result;
    }

    public String toString() {
        return "Value01_Basics(attribute1=" + this.getAttribute1() + ", attribute2=" + this.getAttribute2() + ", attribute3=" + this.getAttribute3() + ")";
    }
}
```

#### 8.2 `staticConstructor`
参照@Data注解

### 9.@Builder 生成的代码为建造者模式（Builder Pattern）
可以很简洁的创建对象，但此时该对象无法序列化为json且json也不能序列化为对象，还需要提供以下注解：
@NoArgsConstructor+@AllArgsConstructor+@Getter（序列化为json时需要）+@Setter（反序列化为对象时需要）。

#### 9.1 基本使用
```java
@Builder
public class Builder01_Basics {
    private String attribute1;
    private String attribute2;
    private String attribute3;
}
```
编译后结果为：
```java
public class Builder01_Basics {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    Builder01_Basics(final String attribute1, final String attribute2, final String attribute3) {
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
    }

    public static Builder01_Basics.Builder01_BasicsBuilder builder() {
        return new Builder01_Basics.Builder01_BasicsBuilder();
    }

    public static class Builder01_BasicsBuilder {
        private String attribute1;
        private String attribute2;
        private String attribute3;

        Builder01_BasicsBuilder() {
        }

        public Builder01_Basics.Builder01_BasicsBuilder attribute1(final String attribute1) {
            this.attribute1 = attribute1;
            return this;
        }

        public Builder01_Basics.Builder01_BasicsBuilder attribute2(final String attribute2) {
            this.attribute2 = attribute2;
            return this;
        }

        public Builder01_Basics.Builder01_BasicsBuilder attribute3(final String attribute3) {
            this.attribute3 = attribute3;
            return this;
        }

        public Builder01_Basics build() {
            return new Builder01_Basics(this.attribute1, this.attribute2, this.attribute3);
        }

        public String toString() {
            return "Builder01_Basics.Builder01_BasicsBuilder(attribute1=" + this.attribute1 + ", attribute2=" + this.attribute2 + ", attribute3=" + this.attribute3 + ")";
        }
    }
}
```
- `Builder01_BasicsBuilder b = Builder01_Basics.builder().attribute1("asd").attribute2("asdasd").attribute3("asdasd");`

#### 9.2 @Singular 配合@Builder使用
作用于集合类型的属性上，配合@Builder使用，如：
```java
@Builder
public class Builder02_Singular {
    private String attribute1;
    private String attribute2;
    @Singular(value = "attribute3")
    private List<String> attribute3;
}
```
编译后结果为：
```java
public class Builder02_Singular {
    private String attribute1;
    private String attribute2;
    private List<String> attribute3;

    Builder02_Singular(final String attribute1, final String attribute2, final List<String> attribute3) {
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
    }

    public static Builder02_Singular.Builder02_SingularBuilder builder() {
        return new Builder02_Singular.Builder02_SingularBuilder();
    }

    public static class Builder02_SingularBuilder {
        private String attribute1;
        private String attribute2;
        private ArrayList<String> attribute3;

        Builder02_SingularBuilder() {
        }

        public Builder02_Singular.Builder02_SingularBuilder attribute1(final String attribute1) {
            this.attribute1 = attribute1;
            return this;
        }

        public Builder02_Singular.Builder02_SingularBuilder attribute2(final String attribute2) {
            this.attribute2 = attribute2;
            return this;
        }

        public Builder02_Singular.Builder02_SingularBuilder attribute3(final String attribute3) {
            if (this.attribute3 == null) {
                this.attribute3 = new ArrayList();
            }

            this.attribute3.add(attribute3);
            return this;
        }

        public Builder02_Singular.Builder02_SingularBuilder attribute3(final Collection<? extends String> attribute3) {
            if (attribute3 == null) {
                throw new NullPointerException("attribute3 cannot be null");
            } else {
                if (this.attribute3 == null) {
                    this.attribute3 = new ArrayList();
                }

                this.attribute3.addAll(attribute3);
                return this;
            }
        }

        public Builder02_Singular.Builder02_SingularBuilder clearAttribute3() {
            if (this.attribute3 != null) {
                this.attribute3.clear();
            }

            return this;
        }

        public Builder02_Singular build() {
            List attribute3;
            switch(this.attribute3 == null ? 0 : this.attribute3.size()) {
            case 0:
                attribute3 = Collections.emptyList();
                break;
            case 1:
                attribute3 = Collections.singletonList(this.attribute3.get(0));
                break;
            default:
                attribute3 = Collections.unmodifiableList(new ArrayList(this.attribute3));
            }

            return new Builder02_Singular(this.attribute1, this.attribute2, attribute3);
        }

        public String toString() {
            return "Builder02_Singular.Builder02_SingularBuilder(attribute1=" + this.attribute1 + ", attribute2=" + this.attribute2 + ", attribute3=" + this.attribute3 + ")";
        }
    }
}
```
创建语句：
```java
List<String> l = new ArrayList<>();
l.add("测试1");
l.add("测试2");
Builder02_SingularBuilder b = Builder02_Singular.builder().attribute3(l);
```

### 10.@Accessors 配合@Setter注解使用，可生成链式的set方法
注意：fluent和chain需要至少有一个为true。
#### 10.1 `fluent`
默认为false，为true时生成的set方法会return当前对象且get/set方法名都不再包含get/set。
```java
@Accessors(fluent = true)
@Setter
public class Accessors01_Fluent {
    private String attribute1;
    private String attribute2;
    private String attribute3;
}
```
编译后结果为：
```java
public class Accessors01_Fluent {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    public Accessors01_Fluent() {
    }

    public Accessors01_Fluent attribute1(final String attribute1) {
        this.attribute1 = attribute1;
        return this;
    }

    public Accessors01_Fluent attribute2(final String attribute2) {
        this.attribute2 = attribute2;
        return this;
    }

    public Accessors01_Fluent attribute3(final String attribute3) {
        this.attribute3 = attribute3;
        return this;
    }
}
```
#### 10.2 `chain`
默认为false，为true时生成的set方法会return当前对象且get/set方法名为正常的get/set方法格式。
```java
@Accessors(chain = true)
@Setter
public class Accessors02_Chain {
    private String attribute1;
    private String attribute2;
    private String attribute3;
}
```
编译后结果为
```java
public class Accessors02_Chain {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    public Accessors02_Chain() {
    }

    public Accessors02_Chain setAttribute1(final String attribute1) {
        this.attribute1 = attribute1;
        return this;
    }

    public Accessors02_Chain setAttribute2(final String attribute2) {
        this.attribute2 = attribute2;
        return this;
    }

    public Accessors02_Chain setAttribute3(final String attribute3) {
        this.attribute3 = attribute3;
        return this;
    }
}
```
- 当`fluent`和`chain`都为`true`时，以`fluent`为准

10.3 `prefix`
比如属性名是user_name，加上注解 @Accessors(prefix = "user_")，生成的get/set方法中则会自动去掉“user_”前缀。其它不是以"user_"开头的属性则不会生成get/set方法。
```java
@Accessors(prefix = "pre_", chain = true)
@Setter
public class Accessors03_Prefix {
    private String pre_attribute1;
    private String attribute2;
}
```
编译后结果为:
```java
public class Accessors03_Prefix {
    private String pre_attribute1;
    private String attribute2;

    public Accessors03_Prefix() {
    }

    public Accessors03_Prefix setAttribute1(final String pre_attribute1) {
        this.pre_attribute1 = pre_attribute1;
        return this;
    }
}
```

## 其他注解
### 11.@NonNull 触发空值检查
作用于属性、方法、参数、本地变量上，触发空值检查，为空时会抛出NullPointerException。作用于属性上并配合@Setter使用时生成代码如下：

#### 11.1 基本使用
```
public class NonNull01_Basics {

    private String name;

    public static void getName(@NonNull NonNull01_Basics n) {
        System.out.println(n.name);
    }

    public static void main(String[] args) {
        NonNull01_Basics.getName(null);
    }
}
```
- 如上代码在执行时就会抛出`NullPointerException`异常，主要用于controller层接收数据时使用

#### 11.2 配合`@Setter`使用
```java
@Setter
public class NonNull02_Setter {
    @NonNull
    private String name;
}
```
编译后结果为:
```java
public class NonNull02_Setter {
    @NonNull
    private String name;

    public NonNull02_Setter() {
    }

    public void setName(@NonNull final String name) {
        if (name == null) {
            throw new NullPointerException("name is marked non-null but is null");
        } else {
            this.name = name;
        }
    }
}
```
- 即set方法参数上自动添加`@NonNull`注解

### 12.@Log 作用于类上, 生成log注解
#### 12.1 基本使用
```java
@Log
public class Log01_basics {
    public static void main(String[] args) {
        log.info("测试 @Log 注解");
    }
}
```
- 使用注解后可以直接使用log对象进行日志操作，ombok替我们添加了如下语句：`private static final Logger log = Logger.getLogger(Log01_basics.class.getName());`

### 13.@CleanUp 作用于本地变量，可以完成资源的释放
#### 13.1 基本使用
```java
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
```
编译后结果为:
```java
public class Cleanup01_Basics {
    public Cleanup01_Basics() {
    }

    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream(new File("a.txt"));

            try {
                FileOutputStream fos = new FileOutputStream("b.txt");
                if (Collections.singletonList(fos).get(0) != null) {
                    fos.close();
                }
            } finally {
                if (Collections.singletonList(fis).get(0) != null) {
                    fis.close();
                }

            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }
}
```
- 即自动添加了`close()`方法进行资源的释放

### 14.@Synchronized 作用于方法，给方法体加锁
```java
public class Synchronized01_Basics {
    @Synchronized
    public void test(){
        System.out.println("test @Synchronized");
    }
}
```
编译后结果为：
```java
public class Synchronized01_Basics {
    private final Object $lock = new Object[0];

    public Synchronized01_Basics() {
    }

    public void test() {
        synchronized(this.$lock) {
            System.out.println("test @Synchronized");
        }
    }
}
```

#### 14.2 `name`
使用@Synchronized("name")，其中name为属性名, 属性必须要存在。
```java
public class Synchronized02_name {
    String name = "";
    @Synchronized("name")
    public void test(){
        System.out.println("test @Synchronized");
    }
}
```
编译后结果为：
```java
public class Synchronized02_name {
    String name = "";

    public Synchronized02_name() {
    }

    public void test() {
        synchronized(this.name) {
            System.out.println("test @Synchronized");
        }
    }
}
```

### 15.@SneakyThrows 偷偷抛出Checked Exception
作用于方法上，偷偷抛出Checked Exception，而无需在方法上的throws子句中声明需要抛出的异常。
```java
public class SneakyThrows01_Basics {
    @SneakyThrows
    public static void main(String[] args) {
        FileReader fileReader = new FileReader("a.txt");
    }
}
```
编译后结果为：
```java
public class SneakyThrows01_Basics {
    public SneakyThrows01_Basics() {
    }

    public static void main(String[] args) {
        try {
            new FileReader("a.txt");
        } catch (Throwable var2) {
            throw var2;
        }
    }
}
```
- 也可以指定异常，如：@SneakyThrows(FileNotFoundException.class)。当指定了异常之后，lombok就会只捕捉我们指定的一种或者几种类型的异常，如果我们指定的类型没有被捕捉到，就会被抛到上一层。

### 16.@Delegate 实现代理模式
#### 16.1 基本使用
```java
public class Delegate01_Basics {
    private interface SimpleCollection {
        boolean add(String item);
        boolean remove(Object item);
    }

    @Delegate(types=SimpleCollection.class)
    private final Collection<String> collection = new ArrayList<String>();
}
```
编译后结果为：
```java
public class Delegate01_Basics {
    private final Collection<String> collection = new ArrayList();

    public Delegate01_Basics() {
    }

    public boolean add(final String item) {
        return this.collection.add(item);
    }

    public boolean remove(final Object item) {
        return this.collection.remove(item);
    }

    private interface SimpleCollection {
        boolean add(String item);

        boolean remove(Object item);
    }
}
```

### 17.var/val 表示变量

#### 17.1 `var`
var可以用来表示任何类型的变量
#### 17.2 `val`
val可以用来表示任何类型的常量