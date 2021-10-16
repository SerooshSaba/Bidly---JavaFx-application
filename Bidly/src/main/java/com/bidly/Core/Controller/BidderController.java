package com.bidly.Core.Controller;

import Adapter.DatabaseAdapter;
import Adapter.PaymentServiceAdapter;

import com.bidly.Core.Application;
import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BidderController extends Controller {

    // Adapter
    DatabaseAdapter databaseAdapter = new DatabaseAdapter();
    PaymentServiceAdapter paymentService = new PaymentServiceAdapter();

    // Globals
    private int ANTIQE_ID;
    private int BID_AMOUNT;
    private Label BID_MESSAGE;
    private Label CURRENT_BID_OUTPUT;

    @FXML
    private ImageView logo;

    @FXML
    private ScrollPane productOverviewContainer;
    @FXML
    private VBox productListingContainer;

    @FXML
    private VBox paymentContainer;
    @FXML
    private ImageView paymentProcessorsImage;
    @FXML
    private TextField cardNumber;
    @FXML
    private TextField expirationDate;
    @FXML
    private TextField securityCode;

    @FXML
    private Button PayButton;

    @FXML
    private VBox paymentSuccessMessageContainer;
    @FXML
    private VBox paymentFailureMessageContainer;

    public void initialize() throws SQLException {
        // Load navigation logo
        Image logoImage = new Image(String.valueOf(Application.class.getResource("/images/logo.PNG")));
        logo.setPreserveRatio(true);
        logo.setFitWidth(75);
        logo.setTranslateX(-30);
        logo.setImage(logoImage);

        //// Load paymentSuccessMessageContainer
        // Image
        Image successImage = new Image(String.valueOf(Application.class.getResource("/images/success.PNG")));
        ImageView successIcon = new ImageView(successImage);
        successIcon.setPreserveRatio(true);
        successIcon.setFitWidth(75);
        successIcon.setImage(successImage);
        // Label
        Label successLabel = new Label("Payment processed successfully.");
        successLabel.setStyle("-fx-text-fill:green");
        // Back button
        Button success_container_back_button = new Button("Back to bidding");
        success_container_back_button.setOnAction(e -> {
            paymentSuccessMessageContainer.setVisible(false);
            paymentSuccessMessageContainer.setManaged(false);
            paymentFailureMessageContainer.setVisible(false);
            paymentFailureMessageContainer.setManaged(false);
            productOverviewContainer.setVisible(true);
            productOverviewContainer.setManaged(true);
        });
        paymentSuccessMessageContainer.getChildren().addAll(successIcon,successLabel,success_container_back_button);

        //// Load paymentFailureMessageContainer
        // Image
        Image failureImage = new Image(String.valueOf(Application.class.getResource("/images/failure.PNG")));
        ImageView failureIcon = new ImageView(failureImage);
        failureIcon.setPreserveRatio(true);
        failureIcon.setFitWidth(75);
        failureIcon.setImage(failureImage);
        // Label
        Label failureLabel = new Label("Payment failed to process.");
        failureLabel.setStyle("-fx-text-fill:red");
        // Back button
        Button failure_container_back_button = new Button("Back to bidding");
        failure_container_back_button.setOnAction(e -> {
            paymentSuccessMessageContainer.setVisible(false);
            paymentSuccessMessageContainer.setManaged(false);
            paymentFailureMessageContainer.setVisible(false);
            paymentFailureMessageContainer.setManaged(false);
            productOverviewContainer.setVisible(true);
            productOverviewContainer.setManaged(true);
        });
        paymentFailureMessageContainer.getChildren().addAll( failureIcon, failureLabel, failure_container_back_button );

        //// Load payment processor popup container
        Image processorsImage = new Image(String.valueOf(Application.class.getResource("/images/paymentprocessors.JPG")));
        paymentProcessorsImage.setImage(processorsImage);
        paymentProcessorsImage.setPreserveRatio(true);
        paymentProcessorsImage.setFitWidth(175);

        //// Get all products on platform
        String query_string = "SELECT antiqes.antiqe_id, antiqes.name, antiqes.description, antiqes.pic_url, antiqes.price, MAX(bids.amount) AS last_bid, stores.name AS storename FROM antiqes " +
                              "LEFT OUTER JOIN bids ON bids.antiqe_id = antiqes.antiqe_id " +
                              "INNER JOIN stores ON antiqes.store_id = stores.store_id " +
                              "GROUP BY antiqes.antiqe_id;";
        databaseAdapter.statement(query_string);
        ResultSet result = databaseAdapter.getResult();
        // Render products
        HBox row = createListRow();
        int product = 1;
        while ( result.next() ) {
            if ( product % 4 == 0 && product != 0 ) {
                row.getChildren().add( createProductView( result.getInt("antiqe_id"), result.getString("name"), result.getString("description"), result.getString("pic_url"), result.getInt("price"), result.getInt("last_bid"), result.getString("storename") ) );
                productListingContainer.getChildren().add(row);
                row = createListRow();
            } else {
                row.getChildren().add( createProductView( result.getInt("antiqe_id"), result.getString("name"), result.getString("description"), result.getString("pic_url"), result.getInt("price"), result.getInt("last_bid"), result.getString("storename") ) );
            }
            product++;
        }
        productListingContainer.getChildren().add(row);
        databaseAdapter.close();
    }

    @FXML
    private void bidClick( int antiqe_id, int current_bid_price, int bid_amount, Label bid_message, Label current_bid_output ) throws SQLException {

        // Find if there are bids for this product
        databaseAdapter.statement("SELECT COUNT(*) AS bids FROM bids WHERE antiqe_id = ? ");
        databaseAdapter.passInt(antiqe_id);
        ResultSet result = databaseAdapter.getResult();
        int bids = Integer.parseInt(result.getString("bids"));
        databaseAdapter.close();

        // If there are no bids
        if ( bids == 0 ) {
            // If bid is higher than the start bid price, process the bid further
            if ( bid_amount > current_bid_price ) {

                // Toggle payment popup
                paymentContainer.setVisible(true);
                paymentContainer.setManaged(true);
                // Toggle product overview container
                productOverviewContainer.setVisible(false);
                productOverviewContainer.setManaged(false);

                // Send the product data to the global data variables
                this.ANTIQE_ID = antiqe_id;
                this.BID_AMOUNT = bid_amount;
                this.BID_MESSAGE = bid_message;
                this.CURRENT_BID_OUTPUT = current_bid_output;

                // The payment popup will do futher process the data

            } else {
                bid_message.setText("Bid must be higher than starting bid price");
                bid_message.setStyle("-fx-font-size:8.5;-fx-text-fill:red");
                bid_message.setVisible(true);
            }
        // If there are bids
        } else {

            // Find the highest bid for the selected product
            databaseAdapter.statement("SELECT MAX(amount) as maxbid FROM bids WHERE antiqe_id = ?");
            databaseAdapter.passInt(antiqe_id);
            int highest_bid = databaseAdapter.getResult().getInt("maxbid");
            databaseAdapter.close();

            // If the current bid is higher then the previous bid, process the bid further
            if ( bid_amount > highest_bid ) {

                // Toggle payment popup
                paymentContainer.setVisible(true);
                paymentContainer.setManaged(true);
                // Toggle product overview container
                productOverviewContainer.setVisible(false);
                productOverviewContainer.setManaged(false);

                // Send the product data to the global data variables
                this.ANTIQE_ID = antiqe_id;
                this.BID_AMOUNT = bid_amount;
                this.BID_MESSAGE = bid_message;
                this.CURRENT_BID_OUTPUT = current_bid_output;

                // The payment popup will do futher process the data

            // If the current bid is lower than the previous bid, tell the user
            } else {
                bid_message.setText("Bid must be higher then previous bid");
                bid_message.setStyle("-fx-font-size:8.5;-fx-text-fill:red");
                bid_message.setVisible(true);
            }
        }
    }

    @FXML
    private void payButtonClick() throws SQLException {

        // Check if the payment information is correct
        if ( paymentService.processPayment( cardNumber.getText(), expirationDate.getText(), securityCode.getText() )) {
            // Show and hide containers
            paymentContainer.setVisible(false);
            paymentContainer.setManaged(false);
            paymentSuccessMessageContainer.setVisible(true);
            paymentSuccessMessageContainer.setManaged(true);
            // Update the bid in the product overview
            CURRENT_BID_OUTPUT.setText(String.valueOf(BID_AMOUNT)+"$");
            // Insert the bid into the databaseAdapter system
            databaseAdapter.statement("INSERT INTO bids VALUES (NULL,?,?)");
            databaseAdapter.passInt(BID_AMOUNT);
            databaseAdapter.passInt(ANTIQE_ID);
            databaseAdapter.execute();
            databaseAdapter.close();
            // Bid succeeded message
            BID_MESSAGE.setText("Bid placed!");
            BID_MESSAGE.setStyle("-fx-font-size:12.5;-fx-text-fill:green;-fx-font-weight:bold");
            BID_MESSAGE.setVisible(true);
        } else {
            // Show and hide containers
            paymentContainer.setVisible(false);
            paymentContainer.setManaged(false);
            paymentFailureMessageContainer.setVisible(true);
            paymentFailureMessageContainer.setManaged(true);
            // Bid failed message in the product container
            BID_MESSAGE.setText("Payment failed!");
            BID_MESSAGE.setStyle("-fx-font-size:12.5;-fx-text-fill:red;-fx-font-weight:bold");
            BID_MESSAGE.setVisible(true);
        }

    }

    @FXML
    protected void logoutClick(ActionEvent actionEvent) throws IOException {
        this.changeView(actionEvent,"authenticationView.fxml");
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
            bid_amount.setText(String.valueOf(last_bid) + "$");
        } else {
            bid_text.setText( "Starting bid price: " );
            bid_amount.setText(String.valueOf(price) + "$");
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
                    this.bidClick( product_id, last_bid, bid_amount_converted, message_output, bid_amount );
                } else {
                    this.bidClick( product_id, price, bid_amount_converted, message_output, bid_amount );
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
