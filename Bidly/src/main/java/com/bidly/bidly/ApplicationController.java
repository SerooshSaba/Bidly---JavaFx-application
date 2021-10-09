package com.bidly.bidly;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationController {

    @FXML
    private ImageView logoImage;
    @FXML
    private ComboBox<String> userTypes;

    public void initialize(){
        Image image = new Image(String.valueOf(Application.class.getResource("/images/logo.png")));
        this.logoImage.setImage(image);
        this.logoImage.setPreserveRatio(true);
        this.logoImage.setFitWidth(90);
        this.userTypes.getItems().setAll("Admin", "Auctioneer", "Bidder");
    }

    @FXML
    protected void LoginClick(ActionEvent event) throws IOException {

        String UserSelection = userTypes.getSelectionModel().selectedItemProperty().getValue();

        if (  UserSelection.equals("Admin")  ) {
            FXMLLoader root = new FXMLLoader(Application.class.getResource("adminview.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root.load(), 850, 600);
            stage.setScene(scene);
            stage.show();
        }
        else if ( UserSelection.equals("Auctioneer") ) {
            FXMLLoader root = new FXMLLoader(Application.class.getResource("storeownerview.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root.load(), 850, 800);
            stage.setScene(scene);
            stage.show();
        }
        else {
            System.out.println("Logging in as bidder!");
        }

    }

}