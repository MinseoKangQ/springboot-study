package com.example.objectmapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private String name;
    private int age;
    @JsonProperty("phone_number")
    private String phoneNumber;

    public User() {
        this.name = null;
        this.age = 0;
        this.phoneNumber = null;
    }

    public User(String name, int age, String phoneNumber) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    // getDefaultUser로 하면 Object Mapper가 잘못 인식하기 때문에 get 없애야 함
    public User defaultUser() {
        return new User("default", 0, "010-1111-3333");
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}