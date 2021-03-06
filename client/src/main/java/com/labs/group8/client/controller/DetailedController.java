package com.labs.group8.client.controller;

import com.labs.group8.client.Client;
import com.labs.group8.client.model.Book;
import com.labs.group8.client.model.BookInstance;
import com.labs.group8.client.model.BookInstances;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DetailedController {
    @FXML private Label detailedID;
    @FXML private Label detailedAuthors;
    @FXML private Label detailedTitle;
    @FXML private Label detailedDate;
    @FXML private Label detailedPages;
    @FXML private Label detailedIssued;

    private Stage stage;

    public void findInstanceByIndex(int index) {
        BookInstances bookInstances = Client.loadSavedBookInstances();
        BookInstance bookInstance = bookInstances.getList().get(index);
        Book book;

        if (index < bookInstance.getBooks().size()) {
            book = bookInstance.getBooks().get(index);
        } else {
            bookInstance.getBooks().add(new Book());
            book = bookInstance.getBooks().get(index - 1);
        }

        detailedID.setText(String.valueOf(bookInstance.getId()));
        detailedAuthors.setText(book.getAuthors());
        detailedTitle.setText(book.getTitle());
        detailedDate.setText(String.valueOf(book.getDate()));
        detailedPages.setText(String.valueOf(book.getPages()));
        detailedIssued.setText(String.valueOf(bookInstance.isIssued()));

        stage.setOnCloseRequest((windowEvent) -> stage.close());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
