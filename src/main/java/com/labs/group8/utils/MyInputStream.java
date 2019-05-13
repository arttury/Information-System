package com.labs.group8.utils;

import java.io.FilterInputStream;
import java.io.InputStream;

public class MyInputStream extends FilterInputStream {

    public MyInputStream(InputStream in) {
        super(in);
    }

    @Override
    public void close() {
        System.out.println("Closing");
    }
}