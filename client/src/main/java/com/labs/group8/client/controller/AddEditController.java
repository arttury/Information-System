package com.labs.group8.client.controller;

import com.labs.group8.client.Client;
import com.labs.group8.client.model.Book;
import com.labs.group8.client.model.BookInstance;
import com.labs.group8.client.model.Books;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AddEditController {

    private static final Logger LOGGER = LogManager.getLogger(AddEditController.class);

    @FXML private TextField idTextField;
    @FXML private ChoiceBox bookChoiceBox;
    @FXML private CheckBox issuedCheckBox;

    private Stage stage;
    private ObservableList bookObservableList = FXCollections.observableArrayList();
    private ArrayList<Book> list = new ArrayList<>();

    public void initialize() {
        Books books = Client.loadSavedData();
        if (books != null) {
            bookObservableList.addAll(books.getBookList());
            bookChoiceBox.setItems(bookObservableList);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void add() {
        Book book = (Book) bookChoiceBox.getSelectionModel().getSelectedItem();
        int id = Integer.parseInt(idTextField.getText());
        list.add(book);
        boolean isIssued = issuedCheckBox.isSelected();

        BookInstance instance = new BookInstance(id, list, isIssued);

        Client.addBookInstance(instance);
        stage.close();
    }

    public void edit() {
        int index = bookChoiceBox.getSelectionModel().getSelectedIndex();
        Book book = (Book) bookChoiceBox.getSelectionModel().getSelectedItem();
        list.add(index, book);

        int id = Integer.parseInt(idTextField.getText());
        boolean isIssued = issuedCheckBox.isSelected();

        BookInstance instance = new BookInstance(id, list, isIssued);
        Client.editBookInstance(id, instance);
        LOGGER.info(instance);
        stage.close();
    }
}
