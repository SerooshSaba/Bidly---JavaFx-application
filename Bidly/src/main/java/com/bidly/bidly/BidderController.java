package com.bidly.bidly;

import Utility.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventListener;

// TODO Create a payment processor windo

public class BidderController {

    @FXML
    private ImageView logo;
    @FXML
    private VBox paymentContainer;

    @FXML
    private VBox paymentSuccessMessageContainer;
    @FXML
    private ImageView successIcon;

    @FXML
    private VBox paymentFailureMessageContainer;
    @FXML
    private VBox productListingContainer;

    Database database = new Database();

    public void initialize() throws SQLException {
        // Load nav logo
        Image logoImage = new Image(String.valueOf(Application.class.getResource("/images/logo.PNG")));
        logo.setPreserveRatio(true);
        logo.setFitWidth(75);
        logo.setTranslateX(-30);
        logo.setImage(logoImage);

        // Load success logo
        Image successImage = new Image(String.valueOf(Application.class.getResource("/images/logo.PNG")));
        successIcon = new ImageView(successImage);
        successIcon.setPreserveRatio(true);
        successIcon.setFitWidth(75);
        successIcon.setTranslateX(-30);

        // Get all products on platform
        String query_string = "SELECT antiqes.antiqe_id, antiqes.name, antiqes.description, antiqes.pic_url, antiqes.price, MAX(bids.amount) AS last_bid, stores.name AS storename FROM antiqes " +
                              "LEFT OUTER JOIN bids ON bids.antiqe_id = antiqes.antiqe_id " +
                              "INNER JOIN stores ON antiqes.store_id = stores.store_id " +
                              "GROUP BY antiqes.antiqe_id;";
        database.statement(query_string);
        ResultSet result = database.getResult();
        // Render products
        HBox row = createListRow();
        int product = 1;
        while ( result.next() ) {
            if ( product % 4 == 0 && product != 0 ) {
                row.getChildren().add( createProductView( result.getInt("antiqe_id"), result.getString("name"), result.getString("description"), result.getString("pic_url"), result.getInt("price"), result.getInt("last_bid"), result.getString("storename") ) );
                // Send container to main container
                productListingContainer.getChildren().add(row);
                // Create new empty container
                row = createListRow();
            } else {
                // just add product
                row.getChildren().add( createProductView( result.getInt("antiqe_id"), result.getString("name"), result.getString("description"), result.getString("pic_url"), result.getInt("price"), result.getInt("last_bid"), result.getString("storename") ) );
            }
            product++;
        }
        productListingContainer.getChildren().add(row);
        database.close();
    }

    @FXML
    protected void logoutClick(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(Application.class.getResource("authentication.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load(), 750, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void bidClick( int antiqe_id, int current_bid_price, int bid_amount, Label bid_message ) throws SQLException {
        // Find if there are bids for this product
        database.statement("SELECT COUNT(*) AS bids FROM bids WHERE antiqe_id = ? ");
        database.passInt(antiqe_id);
        ResultSet result = database.getResult();

        int bids = Integer.parseInt(result.getString("bids"));
        database.close();

        if ( bids == 0 ) { // If there are no bids
            // If bid is higher than the start bid price
            if ( bid_amount > current_bid_price ) {
                database.statement("INSERT INTO bids VALUES (NULL,?,?)");
                database.passInt(bid_amount);
                database.passInt(antiqe_id);
                database.execute();
                database.close();
                bid_message.setText("Bid success!");
                bid_message.setStyle("-fx-font-size:8.5;-fx-text-fill:green");
                bid_message.setVisible(true);
            } else {
                bid_message.setText("Bid must be higher than previous!");
                bid_message.setStyle("-fx-font-size:8.5;-fx-text-fill:red");
                bid_message.setVisible(true);
            }
        } else { // If there are bids

            // Find the highest bid for product
            database.statement("SELECT MAX(amount) as maxbid FROM bids WHERE antiqe_id = ?");
            database.passInt(antiqe_id);
            int highest_bid = database.getResult().getInt("maxbid");
            database.close();
            // If the current bid is higher then the previous bid, register the bid
            if ( bid_amount > highest_bid ) {
                database.statement("INSERT INTO bids VALUES (NULL,?,?)");
                database.passInt(bid_amount);
                database.passInt(antiqe_id);
                database.execute();
                database.close();
                bid_message.setText("Bid success!");
                bid_message.setStyle("-fx-font-size:8.5;-fx-text-fill:green");
                bid_message.setVisible(true);
            // If it is lower, tell the user
            } else {
                bid_message.setText("Bid must be higher then previous bid");
                bid_message.setStyle("-fx-font-size:8.5;-fx-text-fill:red");
                bid_message.setVisible(true);
            }
        }
    }

    private HBox createListRow() {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER);
        row.setStyle("-fx-background-color:white");
        return row;
    }

    private VBox createProductView( int product_id, String productname, String desc, String picurl, int price, int last_bid, String store_name ) {

        // Create main container
        VBox container = new VBox();
        container.setStyle("-fx-border-color:rgba(0,0,0,0.2);-fx-border-radius:10;-fx-background-color:rgba(0,0,0,0.1)");
        container.setSpacing(5);
        container.setMinHeight(250);
        container.setMinWidth(180);
        container.setAlignment(Pos.CENTER);

        // Label for store name
        Label storename = new Label(store_name);
        storename.setStyle("-fx-font-size:10");

        // Label for product name
        Label name = new Label( productname );
        name.setStyle("-fx-font-size:12.5;-fx-font-weight:bold");

        // ImageView for image of product
        Image image_object = new Image(picurl,true);
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

        // Label for start price / last bid
        HBox bid_data_container = new HBox();
        bid_data_container.setAlignment(Pos.CENTER);

        Label bid_text = new Label();
        Label bid_amount = new Label();
        if ( last_bid != 0 ) {
            bid_text.setText( "Last bid: " );
            bid_amount.setText(String.valueOf(last_bid));
        } else {
            bid_text.setText( "Starting bid price: " );
            bid_amount.setText(String.valueOf(price));
        }

        bid_amount.setStyle("-fx-text-fill:green");
        bid_data_container.getChildren().addAll(bid_text,bid_amount);

        // Message label
        Label message_output = new Label();
        message_output.setStyle("-fx-font-size:8.5");
        message_output.setVisible(false);

        // Bid input & Bid Button
        HBox bid_input_container = new HBox();
        bid_input_container.setAlignment(Pos.CENTER);
        TextField bid_field = new TextField();
        Button bid_button = new Button("bid");
        bid_field.setPromptText("$$$");
        bid_field.setMaxWidth(75);

        bid_button.setOnAction(e -> {
            try {
                int bid_amount_converted = Integer.parseInt( bid_field.getText() );

                if ( last_bid != 0 ) {
                    this.bidClick( product_id, last_bid, bid_amount_converted, message_output );
                } else {
                    this.bidClick( product_id, price, bid_amount_converted, message_output );
                }

            } catch (NumberFormatException | SQLException exception ) {
                message_output.setVisible(true);
                message_output.setText("Input a number");
                message_output.setStyle("-fx-font-size:8.5;-fx-text-fill:red");
            }
        });

        bid_input_container.getChildren().addAll(bid_field,bid_button);
        container.getChildren().addAll( storename, name, image, description, bid_data_container, message_output, bid_input_container );
        return container;
    }

}
