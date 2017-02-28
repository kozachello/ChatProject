package home.view;

import home.client.Client;
import home.control.ConnectionControl;
import home.server.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Козак on 14.02.2017.
 */
/**
 * <h1>View MainApp</h1>
 * GUI til {@link home.view.MainChat}
 * - mainChat
 *
 * @author Козак
 */
public class MainChat extends Application {

    private TextArea chatarea = new TextArea(); // main area for chat
    private boolean isServer = true;
    private ConnectionControl connection = isServer ? createServer() : createClient();

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

    private Server createServer() {
        return new Server(19000, data -> {
            Platform.runLater(() -> {   // sender denne tråde i GUI app på ukendt tidspunkt
                chatarea.appendText(data.toString() + "\n");
            });
        });
    }

    private Client createClient() {
        return new Client("localhost", 19000, data -> {
            Platform.runLater(() -> {   // sender denne tråde i GUI app på ukendt tidspunkt
                chatarea.appendText(data.toString() + "\n");
            });
        });
    }

    public static void main(String args[]) throws Exception {
        launch(args);
    }

}