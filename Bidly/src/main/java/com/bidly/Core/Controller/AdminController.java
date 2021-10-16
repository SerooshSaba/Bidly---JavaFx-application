package com.bidly.Core.Controller;

import Adapter.DatabaseAdapter;

import com.bidly.Core.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.SQLException;

public class AdminController {

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
        databaseAdapter.statement("SELECT COUNT(*) as platform_stores FROM stores");
        int stores = databaseAdapter.getResult().getInt("platform_stores");
        if ( stores == 1 ) {
            StoreAmount.setText( String.valueOf(stores) + " Store" );
        } else {
            StoreAmount.setText( String.valueOf(stores) + " Stores" );
        }

        databaseAdapter.statement("SELECT COUNT(*) as platform_products FROM antiqes");
        int products = databaseAdapter.getResult().getInt("platform_products");
        if ( products == 1 ) {
            ProductAmount.setText( products + " Product" );
        } else {
            ProductAmount.setText( products + " Products" );
        }

        EarnedAmount.setText("2445$");

    }

}
