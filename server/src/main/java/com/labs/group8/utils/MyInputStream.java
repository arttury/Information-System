package com.labs.group8.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FilterInputStream;
import java.io.InputStream;

public class MyInputStream extends FilterInputStream {

    private static final Logger LOGGER = LogManager.getLogger(MyInputStream.class);

    public MyInputStream(InputStream in) {
        super(in);
    }

    @Override
    public void close() {
        LOGGER.info("Closing");
    }
}