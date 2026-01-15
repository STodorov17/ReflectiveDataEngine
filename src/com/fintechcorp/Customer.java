package com.fintechcorp;

import com.fintechcorp.annotations.*;

@FileSource(delimiter = ",")
public class Customer {

    @Column(index = 0, name = "name")
    @NotNull
    private String name;

    @Column(index = 1, name = "email")
    @Regex(pattern = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email address")
    private String email;

    @Column(index = 2, name = "age")
    @Range(min = 18, max = 120, message = "Age must be 18+")
    private int age;

    public Customer() {}

    @Override
    public String toString() {
        return "Customer{name='" + name + "', email='" + email + "', age=" + age + "}";
    }
}