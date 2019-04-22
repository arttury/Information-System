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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Client {
    private static final int PORT = 4321;
    private static final Logger LOGGER = LogManager.getLogger(Client.class);

    public static void main(String[] args) {
        int indexOne = 1;
//        add();
//        loadSavedData();
//        edit(indexOne);
//        delete(indexOne);
//        addBookInstance();
//        deleteBookInstance(indexOne);
//        editBookInstance(indexOne);
//        loadSavedBookInstances();

//        List list = new ArrayList();
//        list.add(3);
//        list.add(1);
//        list.add(2);
//        List list2;
//        list2 = (List) list.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
//        System.out.println(list);
//        System.out.println(list2);
    }
//
    private static void edit(int index) {
        try( Socket socket = new Socket("localhost", PORT);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())
        ) {
            JAXBContext bookContext = JAXBContext.newInstance(Books.class);
            Marshaller bookMarshaller = bookContext.createMarshaller();

            Book book = new Book("Another book", "Another author",
                    LocalDate.now(), 100);

            outputStreamWriter.write("edit" + index);
            bookMarshaller.marshal(book, outputStreamWriter);
            outputStreamWriter.flush();
            LOGGER.info("edit method completed");
        } catch (IOException | JAXBException e) {
            LOGGER.info(e);
        }
    }

    private static void editBookInstance(int index) {
        try( Socket socket = new Socket("localhost", PORT);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())
        ) {
            JAXBContext bookContext = JAXBContext.newInstance(BookInstance.class);
            Marshaller bookMarshaller = bookContext.createMarshaller();

            Book book = new Book("Another book", "Another author",
                    LocalDate.now(), 100);
            ArrayList<Book> bookList = new ArrayList<>();
            bookList.add(book);
            BookInstance bookInstances = new BookInstance(2, bookList, false);

            outputStreamWriter.write("edtBookInstance" + index);
            bookMarshaller.marshal(bookInstances, outputStreamWriter);
            outputStreamWriter.flush();
            LOGGER.info("edit method completed");
        } catch (IOException | JAXBException e) {
            LOGGER.info(e);
        }
    }

    private static void add() {
        try( Socket socket = new Socket("localhost", PORT);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())
         ) {
            JAXBContext bookContext = JAXBContext.newInstance(Books.class);
            Marshaller bookMarshaller = bookContext.createMarshaller();

            Book book = new Book("Game", "Vasyan", LocalDate.parse("2000-10-10"), 1000);

            outputStreamWriter.write("add");
            bookMarshaller.marshal(book, outputStreamWriter);
            outputStreamWriter.flush();
            LOGGER.info("add method completed");
        } catch (IOException | JAXBException e) {
            LOGGER.info(e);
        }
    }

    private static void addBookInstance() {
        try( Socket socket = new Socket("localhost", PORT);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())
         ) {
            JAXBContext bookContext = JAXBContext.newInstance(BookInstances.class);
            Marshaller bookMarshaller = bookContext.createMarshaller();

            Book book = new Book("Game of thrones", "Martin", LocalDate.parse("2000-10-10"), 1000);
            ArrayList<Book> bookList = new ArrayList<>();
            bookList.add(book);
            BookInstance bookInstances = new BookInstance(1, bookList, true);

            outputStreamWriter.write("adBookInstance");
            bookMarshaller.marshal(bookInstances, outputStreamWriter);
            outputStreamWriter.flush();
            LOGGER.info("add book instance method completed");
        } catch (IOException | JAXBException e) {
            LOGGER.info(e);
        }
    }

    private static void delete(int index) {
        try(Socket socket = new Socket("localhost", PORT);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())
        ) {
            outputStreamWriter.write("delete" + index);
            outputStreamWriter.flush();
        } catch (IOException e) {
            LOGGER.info(e);
        }
    }

    private static void deleteBookInstance(int index) {
        try(Socket socket = new Socket("localhost", PORT);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())
        ) {
            outputStreamWriter.write("dltBookInstance" + index);
            outputStreamWriter.flush();
        } catch (IOException e) {
            LOGGER.info(e);
        }
    }

    private static void loadSavedData() {
        try(Socket socket = new Socket("localhost", PORT);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedReader inputBufferedReader = new BufferedReader(new InputStreamReader
                    (new MyInputStream(socket.getInputStream())))
        ) {
            JAXBContext bookContext = JAXBContext.newInstance(Books.class);
            Unmarshaller bookUnmarshaller = bookContext.createUnmarshaller();

            outputStreamWriter.write("load" + "\r\n");
            outputStreamWriter.flush();

            Books book = (Books) bookUnmarshaller.unmarshal(new StreamSource(inputBufferedReader));
            LOGGER.info(book.toString());
        } catch (IOException | JAXBException e) {
            LOGGER.info(e);
        }
    }

    private static void loadSavedBookInstances() {
        try(Socket socket = new Socket("localhost", PORT);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedReader inputBufferedReader = new BufferedReader(new InputStreamReader
                    (new MyInputStream(socket.getInputStream())))
        ) {
            JAXBContext bookInstanceContext = JAXBContext.newInstance(BookInstances.class);
            Unmarshaller bookInstanceUnmarshaller = bookInstanceContext.createUnmarshaller();

            outputStreamWriter.write("lodBookInstances" + "\r\n");
            outputStreamWriter.flush();

            BookInstances bookInstances = (BookInstances) bookInstanceUnmarshaller.unmarshal(new StreamSource
                    (inputBufferedReader));
            LOGGER.info(bookInstances.toString());
        } catch (IOException | JAXBException e) {
            LOGGER.info(e);
        }
    }
}

