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
 *
 * <h1>View MainApp</h1>
 * GUI til {@link home.view.MainChat}
 * - mainChat
 *
 * @author Козак
 */
public class MainChat extends Application {

    private TextArea chatarea = new TextArea(); // main area for vores chat
    private boolean isServer = true;
    private ConnectionControl connection = isServer ? createServer() : createClient(); // checker om det er server eller ej

    private Parent createContent() { // metode for vores kontent

        TextField input = new TextField();
        input.setPrefHeight(30);
        input.setOnAction(event -> { // action for tekstboksen
            String message = isServer ? "Server> " : "Client> "; // hvem sender besked?
            message += input.getText(); // hvem + besked
            input.clear(); // tømme linjen
            chatarea.appendText(message); // sætte tekst ind i chatten
        });
        chatarea.setPrefHeight(420);
        VBox root = new VBox(30, chatarea, input);
        root.setPrefSize(400, 400);
        return root;

    }

    @Override // initialisering før start() metoden (fra Application)
    public void init() throws Exception {
        connection.startConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CHAT");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    @Override // lukker her... (fra Application)
    public void stop() throws Exception {
        connection.closeConnection();
    }

    private Server createServer() {
        return new Server(19000, data -> {
            Platform.runLater(() -> {   // sender denne tråd i GUI app på ukendt tidspunkt (kontrol til baggrunds tråd)
                chatarea.appendText(data.toString() + "\n"); // variabel data er objekt
            });
        });
    }

    private Client createClient() {
        return new Client("localhost", 19000, data -> {
            Platform.runLater(() -> {   // sender denne tråd i GUI app på ukendt tidspunkt (--//--)
                chatarea.appendText(data.toString() + "\n");
            });
        });
    }

    public static void main(String args[]) throws Exception {
        launch(args);
    }

}