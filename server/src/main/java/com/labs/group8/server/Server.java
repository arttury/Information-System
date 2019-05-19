package com.labs.group8.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    private static final int PORT = 4321;

    public static void main(String[] args) {
        try {
            Socket socket;
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                LOGGER.info("Server is ready");
                socket = serverSocket.accept();
                new ClientHandler(socket).run();
            }
        } catch (IOException e) {
            LOGGER.trace(e);
        }
    }
}
