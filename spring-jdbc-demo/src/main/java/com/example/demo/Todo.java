package com.example.demo;

public class Todo {
    private Long id;
    private String name;
    private String data;

    public Todo() {
    }

    public Todo(Long id, String name, String data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format(
            "Todo[id=%d, name='%s', data='%s']",
            id, name, data);
    }

    public Long getId() {
        return id;
    }

    public Todo setId(Long id) {
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