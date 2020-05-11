package com.blaife.listener.simulate;

/**
 * @author Blaife
 * @description 模拟监听器
 * @data 2020/5/11 12:58
 */
public interface SimulateListener {
    // 测试
    public static void main(String[] args) {
        Person p = new Person();

        p.registerLister(new PersonListener() {
            @Override
            public void doEat(Event event) {
                Person person1 = event.getPerson();
                System.out.println(person1 + "正在吃饭");
            }

            @Override
            public void doSleep(Event event) {
                Person person1 = event.getPerson();
                System.out.println(person1 + "正在睡觉");
            }
        });

        p.eat();
    }
}

/**
 * 事件监听器
 */
interface PersonListener {
    void doEat(Event event);

    void doSleep(Event event);
}

/**
 * 事件源
 */
class Person {
    private PersonListener personListener;

    public void eat() {
        personListener.doEat(new Event(this));
    }

    public void sleep() {
        personListener.doSleep(new Event(this));
    }

    public void registerLister(PersonListener personListener) {
        this.personListener = personListener;
    }
}

/**
 * 事件对象
 */
class Event {
    private Person person;

    public Event() { }

    public Event(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}


