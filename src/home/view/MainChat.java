package home.view;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Козак on 14.02.2017.
 */
public class MainChat extends Application {

    private TextArea chatarea = new TextArea(); // main area for chat

    private Parent createContent() { // metode for vores kontent

        TextField input = new TextField();
        input.setPrefHeight(30);
        chatarea.setPrefHeight(420);
        VBox root = new VBox(30, chatarea, input);
        root.setPrefSize(400, 400);
        return root;

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CHAT");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public static void main(String args[]) throws Exception {
        launch(args);
    }

}