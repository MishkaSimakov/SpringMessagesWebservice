package com.example.messages.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class User {
    private String name;
    private Integer age;

    public User(Integer age) {
        this.age = age;
    }
}
