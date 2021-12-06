package Controller;

import BidlyCore.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public abstract class Controller {

    public void changeView(ActionEvent actionEvent, String url, int width, int height ) {
        FXMLLoader root = new FXMLLoader(Application.class.getResource(url));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        try {
            Scene scene = new Scene(root.load(), width, height);
            stage.setScene(scene);
            stage.show();
        } catch ( IOException e ) {
            System.out.println( e );
        }

    }

    @FXML
    public void logoutClick(ActionEvent actionEvent) {
        this.changeView(actionEvent,"mainView.fxml", 850, 750 );
    }

}
