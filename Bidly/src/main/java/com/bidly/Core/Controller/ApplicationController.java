package com.bidly.Core.Controller;

import com.bidly.Core.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ApplicationController extends Controller {

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
            this.changeView( event,"adminView.fxml", 750, 500 );
        }
        else if ( UserSelection.equals("Auctioneer") ) {
            this.changeView( event,"auctioneerView.fxml", 800, 750 );
        }
        else {
            this.changeView( event,"bidderView.fxml", 900, 750 );
        }
    }

}