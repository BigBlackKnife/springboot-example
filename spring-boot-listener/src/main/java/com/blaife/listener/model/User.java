package com.blaife.listener.model;

/**
 * 用户测试类，用于测试监听器（监听 Servlet 上下文对象）
 */
public class User {

    private String name;
    private String age;
    private String pwd;

    public User(String name, String age, String pwd) {
        this.name = name;
        this.age = age;
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getPwd() {
        return pwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
