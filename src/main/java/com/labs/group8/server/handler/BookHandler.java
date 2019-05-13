package com.labs.group8.server.handler;

public interface BookHandler {
    void add(String message);
    void edit(String message);
    void delete(String message);
    String load();
}
