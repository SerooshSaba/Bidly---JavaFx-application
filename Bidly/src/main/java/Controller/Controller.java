package Controller;

import BidlyCore.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public abstract class Controller {

    public void changeView(ActionEvent actionEvent, String url, int width, int height ) throws IOException {
        FXMLLoader root = new FXMLLoader(Application.class.getResource(url));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load(), width, height);
        stage.setScene(scene);
        stage.show();
    }

}
