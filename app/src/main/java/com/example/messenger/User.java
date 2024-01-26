package com.example.messenger;

public class User {
    private String id;
    private String name;
    private String lastName;
    private int age;
    private boolean status;

    public User(String id, String name, String lastName, int age, boolean status) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.status = status;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public boolean getStatus() {
        return status;
    }
}
