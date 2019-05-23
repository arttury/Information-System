package com.labs.group8.server;

import com.labs.group8.server.handler.BookHandler;
import com.labs.group8.server.handler.BookInstancesHandler;
import com.labs.group8.server.handler.BooksHandler;
import com.labs.group8.utils.MyInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ClientHandler implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);
    private Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(BufferedReader inputBufferedReader = new BufferedReader(new InputStreamReader
                (new MyInputStream(socket.getInputStream())));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream())
        ) {
            LOGGER.info("Client connected " + socket.getInetAddress());
            BookHandler booksController = new BooksHandler();
            BookHandler bookInstancesController = new BookInstancesHandler();
            String message = inputBufferedReader.readLine();

            bookHandler(printWriter, booksController, message);

            bookInstanceHandler(printWriter, bookInstancesController, message);
        } catch (IOException e) {
            LOGGER.info(e);
        }
    }

    private void bookInstanceHandler(PrintWriter printWriter, BookHandler bookInstancesController, String message) {
        if (Objects.equals(message, "lodBookInstances")) {
            String xml = bookInstancesController.load();
            printWriter.println(xml + "\r\n");
            printWriter.flush();
        }
        if (message.startsWith("adBookInstance")) {
            bookInstancesController.add(message);
        }
        if (message.startsWith("edtBookInstance")) {
            bookInstancesController.edit(message);
        }
        if (message.startsWith("dltBookInstance")) {
            bookInstancesController.delete(message);
        }
    }

    private void bookHandler(PrintWriter printWriter, BookHandler booksController, String message) {
        if (Objects.equals(message, "load")) {
            String xml = booksController.load();
            printWriter.println(xml + "\r\n");
            printWriter.flush();
        }
        if (message.startsWith("add")) {
            booksController.add(message);
        }
        if (message.startsWith("edit")) {
            booksController.edit(message);
        }
        if (message.startsWith("delete")) {
            booksController.delete(message);
        }
    }
}
