package com.labs.group8.client;

import com.labs.group8.client.controller.AddEditController;
import com.labs.group8.client.controller.BookOverviewController;
import com.labs.group8.client.controller.DetailedController;
import com.labs.group8.client.model.Book;
import com.labs.group8.client.model.BookInstance;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MainView extends Application {

    private static final Logger LOGGER = LogManager.getLogger(MainView.class);
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Book> bookData = FXCollections.observableArrayList();
    private ObservableList<BookInstance> bookInstanceData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Returns the data as an observable list of Persons.
     */
    public ObservableList<Book> getBookData() {
        return bookData;
    }

    public ObservableList<BookInstance> getBookInstanceData() { return bookInstanceData; }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Library");

        initRootLayout();

        showBookOverview();

        primaryStage.setOnCloseRequest((windowEvent) -> Platform.exit());
    }

    public void addBookInstance() {
        FXMLLoader loader = new  FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/addEdit.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = loader.load();
        } catch (IOException e) {
            LOGGER.info(e);
        }

        Stage stage = new Stage();
        stage.setTitle("Add/Edit Book");
        stage.initOwner(primaryStage);
        Scene scene = null;
        if (anchorPane != null) {
            scene = new Scene(anchorPane);
        }
        stage.setScene(scene);

        AddEditController controller = loader.getController();
        controller.setStage(stage);
        stage.showAndWait();
    }

    public void editBookInstance() {
        System.out.println("Add book instance");
        FXMLLoader loader = new  FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/addEdit.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Add/Edit Book");
        stage.initOwner(primaryStage);
        Scene scene = null;
        if (anchorPane != null) {
            scene = new Scene(anchorPane);
        }
        stage.setScene(scene);

        AddEditController controller = loader.getController();
        controller.setStage(stage);
        stage.showAndWait();
    }

    public void detailedBookInstance(int index) {
        FXMLLoader loader = new  FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/detailed.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Detailed Information");
        stage.initOwner(primaryStage);
        Scene scene = null;
        if (anchorPane != null) {
            scene = new Scene(anchorPane);
        }
        stage.setScene(scene);

        DetailedController controller = loader.getController();
        controller.setStage(stage);
        controller.findInstanceByIndex(index);
        stage.showAndWait();
    }

    /**
     * Initializes the root layout.
     */
    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/rootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    private void showBookOverview() {
        try {
            // Load book overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/BookOverview.fxml"));
            AnchorPane personOverview = loader.load();

            // Set book overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Даём контроллеру доступ к главному приложению.
            BookOverviewController controller = loader.getController();
            controller.setMainView(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}