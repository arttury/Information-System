package com.labs.group8.server.handler;

import com.labs.group8.model.Book;
import com.labs.group8.model.Books;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BooksHandler implements BookHandler {
    private static final Logger LOGGER = LogManager.getLogger(BooksHandler.class);
    private static final File BOOK_FILE = new File(System.getProperty("user.dir"), "/book.xml");

    private static List<Book> bookList = new ArrayList<>();
    private static int counter;

    static {
        try {
            JAXBContext context = JAXBContext.newInstance(Books.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Books books = (Books) unmarshaller.unmarshal(new FileReader(BOOK_FILE));
            bookList.addAll(books.getBookList());
        } catch (JAXBException | FileNotFoundException e) {
            LOGGER.info(e);
        }
    }

    public String load() {
        LOGGER.info("Method load has invoked");

        StringBuilder stringBuilder = new StringBuilder();
        String xml = null;

        try(FileReader fileReader = new FileReader(BOOK_FILE);
            BufferedReader bufferedReader = new BufferedReader(Objects.requireNonNull(fileReader))
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            xml = stringBuilder.toString();
        } catch (IOException e) {
            LOGGER.info(e);
        }
        return xml;
    }

    public void add(String message) {
        LOGGER.info("Add method has invoked");
        String newString = message.substring(3);
        Books books = new Books();
        books.setBookList(bookList);

        try {
            JAXBContext bookContext = JAXBContext.newInstance(Books.class);
            Unmarshaller unmarshaller = bookContext.createUnmarshaller();
            FileOutputStream fileOutputStream = new FileOutputStream(BOOK_FILE);

            Book book = (Book) unmarshaller.unmarshal(new StringReader(newString));
            books.getBookList().add(book);
            counter++;

            Marshaller bookMarshaller = bookContext.createMarshaller();
            bookMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            bookMarshaller.marshal(books, fileOutputStream);
            fileOutputStream.close();
        } catch (JAXBException | IOException e) {
            LOGGER.info(e);
        }
    }

    public void edit(String message) {
        LOGGER.info("Edit method has invoked");
        String newString = message.substring(5);
        int index = Integer.valueOf(message.substring(4, 5));

        Books books = new Books();
        books.setBookList(bookList);

        if (index <= counter) {
            try {
                JAXBContext bookContext = JAXBContext.newInstance(Books.class);
                Unmarshaller unmarshaller = bookContext.createUnmarshaller();
                FileOutputStream fileOutputStream = new FileOutputStream(BOOK_FILE);

                Book book = (Book) unmarshaller.unmarshal(new StringReader(newString));
                books.getBookList().remove(index);
                counter--;
                books.getBookList().add(index, book);
                counter++;

                Marshaller bookMarshaller = bookContext.createMarshaller();
                bookMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                bookMarshaller.marshal(books, fileOutputStream);
                fileOutputStream.close();
            } catch (JAXBException | IOException e) {
                LOGGER.info(e);
            }
        } else try {
            throw new IndexOutOfBoundsException();
        } catch (IndexOutOfBoundsException e) {
            LOGGER.info(e + "index is too big");
        }
    }

    public void delete(String message) {
        LOGGER.info("Delete method has invoked");
        try {
            JAXBContext bookContext = JAXBContext.newInstance(Books.class);
            FileOutputStream fileOutputStream = new FileOutputStream(BOOK_FILE);

            int index = Integer.valueOf(message.substring(6));
            Books books = new Books();
            books.setBookList(bookList);

            books.getBookList().remove(index);
            counter--;

            Marshaller bookMarshaller = bookContext.createMarshaller();
            bookMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            bookMarshaller.marshal(books, fileOutputStream);
            fileOutputStream.close();
        } catch (JAXBException | IOException e) {
            LOGGER.info(e);
        }
    }
}
