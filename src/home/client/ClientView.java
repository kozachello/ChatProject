package home.client;

import home.view.MainChat;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Козак on 14.02.2017.
 *
 * <h1>View MainApp</h1>
 * GUI til {@link home.client.ClientView }
 * - client view.
 *
 * @author Козак
 */
public class ClientView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        MainChat mainChat = new MainChat();
        primaryStage.setTitle("StartWindow");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        Text scenetitle = new Text("Type Your Nickname & Start Chat");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.MEDIUM, 20));
        grid.add(scenetitle, 0, 0, 3, 1);

        Label userName = new Label("Nickname:");
        grid.add(userName, 0, 1);

        TextField userBox = new TextField();
        grid.add(userBox, 1, 1);

        Button button = new Button("Start");
        button.setAlignment(Pos.BASELINE_CENTER);
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(button);
        grid.add(hbox, 1, 4);

        Scene scene = new Scene(grid, 400, 300);
        //ClientDesktop desktop = new ClientDesktop();

        button.setOnAction(event -> {
            // some action here...
            ChatUser user = new ChatUser(userBox.getText());
            System.out.println(user);
            if(user.usernameIsNotNull()) {
                mainChat.setUserName(userBox.getText(), false);
                primaryStage.close();
                try {
                    mainChat.start(new Stage());
                    // must connect to the socket..
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String args[]) {
        //Locale locale = Locale.getDefault(); // не уверен что понадобится
        launch(args);
    }
}

