package com.example.demo;

import org.springframework.data.annotation.Id;

public class Todo {
    @Id
    private String id;
    private String name;
    private String data;

    public Todo() {
    }

    public Todo(String name, String data) {
        this.name = name;
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format(
            "Todo[id=%s, name='%s', data='%s']",
            id, name, data);
    }

    public String getId() {
        return id;
    }

    public Todo setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Todo setName(String name) {
        this.name = name;
        return this;
    }

    public String getData() {
        return data;
    }

    public Todo setData(String data) {
        this.data = data;
        return this;
    }
}