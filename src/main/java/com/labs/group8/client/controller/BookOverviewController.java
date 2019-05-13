package com.labs.group8.client.controller;

import com.labs.group8.client.Client;
import com.labs.group8.client.MainView;
import com.labs.group8.model.Book;
import com.labs.group8.model.BookInstance;
import com.labs.group8.model.BookInstances;
import com.labs.group8.model.Books;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class BookOverviewController {

    @FXML private ListView mainLV;
    @FXML private ListView<Book> listView;

    @FXML private TextField titleTextField;
    @FXML private TextField authorsTextField;
    @FXML private TextField pagesTextField;
    @FXML private DatePicker bookDatePicker;

    private static final Logger LOGGER = LogManager.getLogger(BookOverviewController.class);
    private ObservableList<Book> bookObservableList = FXCollections.observableArrayList();
    private ObservableList<BookInstance> bookInstancesObservableList = FXCollections.observableArrayList();
    private MainView mainView;

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    public void initialize() {
        Books books = Client.loadSavedData();
        if (books != null) {
            bookObservableList.addAll(books.getBookList());
            listView.getItems().addAll(bookObservableList);
        }

        BookInstances bookInstances = Client.loadSavedBookInstances();
        if (bookInstances != null) {
            bookInstancesObservableList.addAll(bookInstances.getList());
            mainLV.setItems(bookInstancesObservableList);
        }
    }

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     *
     * @param mainView для вызова MainView
     */
    public void setMainView(MainView mainView) {
        this.mainView = mainView;

        // Добавление в таблицу данных из наблюдаемого списка
//        mainLV.setItems(mainView.getBookInstanceData());
    }

    private MainView getMainView() {
        return mainView;
    }

    public void add() {
        getMainView().addBookInstance();
        refreshBookInstances();
    }

    public void edit() {
        LOGGER.info("Edit");
        getMainView().editBookInstance();
        refreshBookInstances();
    }

    public void delete() {
        int index = mainLV.getSelectionModel().getSelectedIndex();
        mainLV.getItems().remove(index);
        Client.deleteBookInstance(index);
    }

    private void refreshBookInstances() {
        bookInstancesObservableList.clear();
        BookInstances bookInstances = Client.loadSavedBookInstances();
        bookInstancesObservableList.addAll(bookInstances.getList());
        mainLV.setItems(bookInstancesObservableList);
    }

    public void detailed() {
        int index = mainLV.getSelectionModel().getSelectedIndex();
        getMainView().detailedBookInstance(index);
    }

    public void bookAdd() {
        String title = titleTextField.getText();
        String authors = authorsTextField.getText();
        LocalDate date = bookDatePicker.getValue();
        int pages = Integer.parseInt(pagesTextField.getText());

        Book book = new Book(title, authors, date, pages);
        Client.add(book);

        titleTextField.clear();
        authorsTextField.clear();
        bookDatePicker.getEditor().clear();
        pagesTextField.clear();

        bookObservableList.add(book);
        listView.setItems(bookObservableList);
    }

    public void bookEdit() {
        int index = listView.getSelectionModel().getSelectedIndex();
        String title = titleTextField.getText();
        String authors = authorsTextField.getText();
        LocalDate date = bookDatePicker.getValue();
        int pages = Integer.parseInt(pagesTextField.getText());

        LOGGER.info(title + " " + authors + " " + date + " " + pages);
        Book book = new Book(title, authors, date, pages);
        Client.edit(index, book);

        titleTextField.clear();
        authorsTextField.clear();
        bookDatePicker.getEditor().clear();
        pagesTextField.clear();

        bookObservableList.remove(index);
        bookObservableList.add(index, book);
        listView.setItems(bookObservableList);
    }

    public void bookDelete() {
        int index = listView.getSelectionModel().getSelectedIndex();
        Client.delete(index);
        bookObservableList.remove(index);
        listView.setItems(bookObservableList);
    }
}