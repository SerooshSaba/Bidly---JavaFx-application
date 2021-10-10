package com.bidly.bidly;

import Utility.Database;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BidderController {

    @FXML
    private ImageView logo;
    @FXML
    private ScrollPane productOverviewContainer;
    @FXML
    private VBox paymentContainer;
    @FXML
    private VBox paymentSuccessMessageContainer;
    @FXML
    private VBox paymentFailureMessageContainer;
    @FXML
    private VBox productListingContainer;

    Database database = new Database();

    public void initialize() throws SQLException {

        // Load logo
        Image logoImage = new Image(String.valueOf(Application.class.getResource("/images/logo.PNG")));
        logo.setPreserveRatio(true);
        logo.setFitWidth(75);
        logo.setTranslateX(-30);
        logo.setImage(logoImage);

        // Get all products on platform
        String query_string = "SELECT antiqes.antiqe_id, antiqes.name, antiqes.description, antiqes.pic_url, antiqes.price, stores.name AS storename FROM antiqes INNER JOIN stores on antiqes.store_id = stores.store_id";
        database.statement(query_string);
        ResultSet result = database.getResult();

        HBox row = createListRow();
        int product = 1;
        while ( result.next() ) {
            if ( product % 4 == 0 && product != 0 ) {
                row.getChildren().add( createProductView( result.getInt("antiqe_id"), result.getString("name"), result.getString("description"), result.getString("pic_url"), result.getInt("price"), result.getString("storename") ) );
                // Send container to main container
                productListingContainer.getChildren().add(row);
                // Create new empty container
                row = createListRow();
            } else {
                // just add product
                row.getChildren().add( createProductView( result.getInt("antiqe_id"), result.getString("name"), result.getString("description"), result.getString("pic_url"), result.getInt("price"), result.getString("storename") ) );
            }
            product++;
        }
        productListingContainer.getChildren().add(row);
        database.close();

    }

    private HBox createListRow() {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private VBox createProductView( int id, String productname, String desc, String picurl, int price, String store_name ) {

        // Create main container
        VBox container = new VBox();
        container.setStyle("-fx-border-color:rgba(0,0,0,0.1);-fx-border-radius:10");
        container.setSpacing(5);
        container.setMinHeight(200);
        container.setMinWidth(180);
        container.setAlignment(Pos.CENTER);

        // Label for name
        Label name = new Label( productname );
        name.setStyle("-fx-font-size:12.5;-fx-font-weight:bold");

        // ImageView for image of product
        //Image image_object = new Image(String.valueOf(Application.class.getResource(picurl)),true);
        Image image_object = new Image(String.valueOf(Application.class.getResource("/images/box.PNG")),true);
        ImageView image = new ImageView(image_object);
        image.setFitHeight(75);
        image.setFitWidth(75);

        // Label for description
        Label description = new Label( desc );
        description.setStyle("-fx-font-size:10");
        description.setAlignment(Pos.CENTER);
        description.setMaxHeight(50);
        description.setMaxWidth(175);
        description.setWrapText(true);

        // Label for latest bid
        HBox bid_data_container = new HBox();
        bid_data_container.setAlignment(Pos.CENTER);
        Label bid_text = new Label("Current bid: ");
        Label bid_amount = new Label("2000$");
        bid_amount.setStyle("-fx-text-fill:green");
        bid_data_container.getChildren().addAll(bid_text,bid_amount);

        // Bid input 3 Bid Button
        HBox bid_input_container = new HBox();
        bid_input_container.setAlignment(Pos.CENTER);
        TextField bid_field = new TextField();
        Button bid_button = new Button("bid");
        bid_field.setPromptText("$$$");
        bid_field.setMaxWidth(75);
        bid_input_container.getChildren().addAll(bid_field,bid_button);

        container.getChildren().addAll( name, image, description, bid_data_container, bid_input_container );
        return container;
    }

}
