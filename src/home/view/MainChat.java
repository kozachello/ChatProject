package home.view;

import home.client.ChatUser;
import home.client.Client;
import home.control.ConnectionControl;
import home.server.Server;
import home.socket.ClientSocket;
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
    private static ChatUser user;
    private ClientSocket clientSocket;
    private boolean isServer = true; // skal skiftes mellem true/false (prototype #1)
    private ConnectionControl connection = isServer ? createServer() : createClient(); // checker om det er server eller ej

    /*public static void setUserName(String name, boolean notServer) {
        user = new ChatUser(name);
        isServer = notServer;
    }*/

    /*private String getUserName() {
        return user.toString();
    }*/

    private Parent createContent() { // metode for vores kontent

        TextField input = new TextField();
        input.setPrefHeight(30);
        input.setOnAction(event -> { // action for tekstboksen
            String message = isServer ? "Server> " : "Client> "; // hvem sender besked?
            message += input.getText(); // hvem + besked
            input.clear(); // tømme linjen
            chatarea.appendText(message + "\n"); // sætte tekst ind i chatten
            try {
                // det her er enkelt forbindelse... skal sendes til alle connections
                connection.send(message); // String implementerer Serializable, beskeden kan blive som den er
            } catch (Exception e) {
                chatarea.appendText("something wrong with sending..." + "\n"); // hvis der er ikke nogen ellers
            }
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