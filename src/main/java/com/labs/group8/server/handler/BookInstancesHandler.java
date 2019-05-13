package com.labs.group8.server.handler;

import com.labs.group8.model.BookInstance;
import com.labs.group8.model.BookInstances;
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

public class BookInstancesHandler implements BookHandler {

    private static final Logger LOGGER = LogManager.getLogger(BooksHandler.class);
    private static final File BOOK_FILE = new File("src/main/resources/bookInstance.xml");

    private static List<BookInstance> bookList = new ArrayList<>();
    private static int counter;

    public void add(String message) {
        LOGGER.info("Add method has invoked");
        LOGGER.info(message);
        String msg = message.substring(14);
        BookInstances bookInstances = new BookInstances();
        bookInstances.setList(bookList);

        try {
            JAXBContext bookContext = JAXBContext.newInstance(BookInstances.class);
            Unmarshaller unmarshaller = bookContext.createUnmarshaller();
            FileOutputStream fileOutputStream = new FileOutputStream(BOOK_FILE);

            BookInstance bookInstance = (BookInstance) unmarshaller.unmarshal(new StringReader(msg));
            bookInstances.getList().add(bookInstance);
            counter++;

            Marshaller bookMarshaller = bookContext.createMarshaller();
            bookMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            bookMarshaller.marshal(bookInstances, fileOutputStream);
            fileOutputStream.close();
        } catch (JAXBException | IOException e) {
            LOGGER.info(e);
        }
    }

    public void delete(String message) {
        LOGGER.info("Delete method has invoked");
        try {
            JAXBContext bookContext = JAXBContext.newInstance(BookInstances.class);
            FileOutputStream fileOutputStream = new FileOutputStream(BOOK_FILE);

            int index = Integer.valueOf(message.substring(15));
            LOGGER.info(index);
            BookInstances bookInstances = new BookInstances();
            bookInstances.setList(bookList);

            bookInstances.getList().remove(index);
            counter--;

            Marshaller bookMarshaller = bookContext.createMarshaller();
            bookMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            bookMarshaller.marshal(bookInstances, fileOutputStream);
            fileOutputStream.close();
        } catch (JAXBException | IOException e) {
            LOGGER.info(e);
        }
    }

    public void edit(String message) {
        LOGGER.info("Edit method has invoked");
        String newString = message.substring(16);
        LOGGER.info(newString);
        int index = Integer.valueOf(message.substring(15, 16));

        BookInstances bookInstances = new BookInstances();
        bookInstances.setList(bookList);

        if (index <= counter) {
            try {
                JAXBContext bookContext = JAXBContext.newInstance(BookInstances.class);
                Unmarshaller unmarshaller = bookContext.createUnmarshaller();
                FileOutputStream fileOutputStream = new FileOutputStream(BOOK_FILE);

                BookInstance bookInstance = (BookInstance) unmarshaller.unmarshal(new StringReader(newString));
                bookInstance.getBooks().remove(index);
                counter--;
                bookInstances.getList().add(index, bookInstance);
                counter++;

                Marshaller bookMarshaller = bookContext.createMarshaller();
                bookMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                bookMarshaller.marshal(bookInstances, fileOutputStream);
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
}
