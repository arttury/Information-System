package com.labs.group8.client;

import com.labs.group8.model.Book;
import com.labs.group8.model.BookInstance;
import com.labs.group8.model.BookInstances;
import com.labs.group8.model.Books;
import com.labs.group8.utils.MyInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;

public class Client {

    private static final int PORT = 4321;
    private static final Logger LOGGER = LogManager.getLogger(Client.class);

    public static void add(Book book) {
        try( Socket socket = new Socket("localhost", PORT);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())
         ) {
            JAXBContext bookContext = JAXBContext.newInstance(Books.class);
            Marshaller bookMarshaller = bookContext.createMarshaller();

            outputStreamWriter.write("add");
            bookMarshaller.marshal(book, outputStreamWriter);
            outputStreamWriter.flush();
            LOGGER.info("add method completed");
        } catch (IOException | JAXBException e) {
            LOGGER.info(e);
        }
    }

    public static void edit(int index, Book book) {
        try( Socket socket = new Socket("localhost", PORT);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())
        ) {
            JAXBContext bookContext = JAXBContext.newInstance(Books.class);
            Marshaller bookMarshaller = bookContext.createMarshaller();

            outputStreamWriter.write("edit" + index);
            bookMarshaller.marshal(book, outputStreamWriter);
            outputStreamWriter.flush();
            LOGGER.info("edit method completed");
        } catch (IOException | JAXBException e) {
            LOGGER.info(e);
        }
    }

    public static void delete(int index) {
        try(Socket socket = new Socket("localhost", PORT);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())
        ) {
            outputStreamWriter.write("delete" + index);
            outputStreamWriter.flush();
        } catch (IOException e) {
            LOGGER.info(e);
        }
    }

    public static void addBookInstance(BookInstance bookInstance) {
        try( Socket socket = new Socket("localhost", PORT);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())
         ) {
            JAXBContext bookContext = JAXBContext.newInstance(BookInstances.class);
            Marshaller bookMarshaller = bookContext.createMarshaller();

            outputStreamWriter.write("adBookInstance");
            bookMarshaller.marshal(bookInstance, outputStreamWriter);
            outputStreamWriter.flush();
            LOGGER.info("add book instance method completed");
        } catch (IOException | JAXBException e) {
            LOGGER.info(e);
        }
    }

    public static void editBookInstance(int index, BookInstance bookInstance) {
        try( Socket socket = new Socket("localhost", PORT);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())
        ) {
            JAXBContext bookContext = JAXBContext.newInstance(BookInstance.class);
            Marshaller bookMarshaller = bookContext.createMarshaller();

            outputStreamWriter.write("edtBookInstance" + index);
            bookMarshaller.marshal(bookInstance, outputStreamWriter);
            outputStreamWriter.flush();
            LOGGER.info("edit method completed");
        } catch (IOException | JAXBException e) {
            LOGGER.info(e);
        }
    }

    public static void deleteBookInstance(int index) {
        try(Socket socket = new Socket("localhost", PORT);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())
        ) {
            outputStreamWriter.write("dltBookInstance" + index);
            outputStreamWriter.flush();
        } catch (IOException e) {
            LOGGER.info(e);
        }
    }

    public static Books loadSavedData() {
        Books book = null;
        try(Socket socket = new Socket("localhost", PORT);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedReader inputBufferedReader = new BufferedReader(new InputStreamReader
                    (new MyInputStream(socket.getInputStream())))
        ) {
            JAXBContext bookContext = JAXBContext.newInstance(Books.class);
            Unmarshaller bookUnmarshaller = bookContext.createUnmarshaller();

            outputStreamWriter.write("load" + "\r\n");
            outputStreamWriter.flush();

            book = (Books) bookUnmarshaller.unmarshal(new StreamSource(inputBufferedReader));
        } catch (IOException | JAXBException e) {
            LOGGER.info(e);
        }
        return book;
    }

    public static BookInstances loadSavedBookInstances() {
        BookInstances bookInstances = null;
        try(Socket socket = new Socket("localhost", PORT);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedReader inputBufferedReader = new BufferedReader(new InputStreamReader
                    (new MyInputStream(socket.getInputStream())))
        ) {
            JAXBContext bookInstanceContext = JAXBContext.newInstance(BookInstances.class);
            Unmarshaller bookInstanceUnmarshaller = bookInstanceContext.createUnmarshaller();

            outputStreamWriter.write("lodBookInstances" + "\r\n");
            outputStreamWriter.flush();

            bookInstances = (BookInstances) bookInstanceUnmarshaller.unmarshal(new StreamSource
                    (inputBufferedReader));
        } catch (IOException | JAXBException e) {
            LOGGER.info(e);
        }
        return bookInstances;
    }
}

