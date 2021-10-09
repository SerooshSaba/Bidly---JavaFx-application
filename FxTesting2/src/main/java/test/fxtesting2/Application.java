package test.fxtesting2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;



public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader root = new FXMLLoader(Application.class.getResource("authentication.fxml"));
        Scene scene = new Scene(root.load(), 750, 500);
        stage.setTitle("BIDLY");
        stage.setScene(scene);
        Image image = new Image(String.valueOf(Application.class.getResource("/images/handshake.PNG")));
        stage.getIcons().add(image);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}