package com.bidly.bidly;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Controller {

    public void changeView(ActionEvent actionEvent, String url) throws IOException {
        FXMLLoader root = new FXMLLoader(Application.class.getResource(url));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load(), 750, 500);
        stage.setScene(scene);
        stage.show();
    }

}
