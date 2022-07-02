package com.example.me.sqliteperson;

public class Person {
    private long id;
    private String name;

    public Person() {
        this.id=0;
        this.name="";
    }
    public Person(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Person(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String comment) {
        this.name = comment;
    }

    @Override
    public String toString() {
        return name ;
    }
}
