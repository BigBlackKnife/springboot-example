package com.blaife.rabbitmq.model;

import java.io.Serializable;

public class Model implements Serializable {

    private String name;
    private String synopsis;


    public String getName() {
        return name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Model setName(String name) {
        this.name = name;
        return this;
    }

    public Model setSynopsis(String synopsis) {
        this.synopsis = synopsis;
        return this;
    }

    @Override
    public String toString() {
        return "Model{" +
                "name='" + name + '\'' +
                ", synopsis='" + synopsis + '\'' +
                '}';
    }
}
