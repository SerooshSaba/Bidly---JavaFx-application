package com.bidly.Core.Controller;

import Adapter.DatabaseAdapter;

import com.bidly.Core.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AdminController extends Controller {

    // Images
    @FXML
    private ImageView StoreIcon;
    @FXML
    private ImageView ProductIcon;
    @FXML
    private ImageView PaymentIcon;

    // Platform data
    @FXML
    private Label StoreAmount;
    @FXML
    private Label ProductAmount;
    @FXML
    private Label EarnedAmount;

    DatabaseAdapter databaseAdapter = new DatabaseAdapter();

    public void initialize() throws SQLException {

        // Load images
        Image store_jpg = new Image(String.valueOf(Application.class.getResource("/images/store.PNG")));
        StoreIcon.setFitHeight(30);
        StoreIcon.setFitWidth(30);
        StoreIcon.setImage(store_jpg);

        Image product_jpg = new Image(String.valueOf(Application.class.getResource("/images/box.PNG")));
        ProductIcon.setFitHeight(35);
        ProductIcon.setFitWidth(35);
        ProductIcon.setImage(product_jpg);

        Image money_jpg = new Image(String.valueOf(Application.class.getResource("/images/money.PNG")));
        PaymentIcon.setFitHeight(35);
        PaymentIcon.setFitWidth(35);
        PaymentIcon.setImage(money_jpg);

        // Load platform data
        int stores = databaseAdapter.getAmountOfStores();
        if ( stores == 1 ) {
            StoreAmount.setText( String.valueOf(stores) + " Store" );
        } else {
            StoreAmount.setText( String.valueOf(stores) + " Stores" );
        }

        int products = databaseAdapter.getAmountOfProducts();
        if ( products == 1 ) {
            ProductAmount.setText( products + " Product" );
        } else {
            ProductAmount.setText( products + " Products" );
        }

        EarnedAmount.setText("2445$"); // TODO !!!!!!!!!!!!
    }


    @FXML
    protected void logoutClick(ActionEvent event) throws IOException {
        this.changeView(event,"authenticationView.fxml", 750, 500 );
    }

    @FXML
    protected void storeClick(ActionEvent event) throws IOException {
        this.changeView(event,"stores.fxml", 750, 500 );
    }


}
