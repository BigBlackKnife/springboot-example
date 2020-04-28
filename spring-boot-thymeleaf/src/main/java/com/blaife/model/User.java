package com.blaife.model;

/**
 * @author Blaife
 * @description 用户实体
 * @data 2020/4/28 16:36
 */
public class User {

    private String name;
    private String nickName;
    private int sex;
    private String age;
    private String birthday;

    public User(String name, String nickName, int sex, String age, String birthday) {
        this.name = name;
        this.nickName = nickName;
        this.sex = sex;
        this.age = age;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public int getSex() {
        return sex;
    }

    public String getAge() {
        return age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
