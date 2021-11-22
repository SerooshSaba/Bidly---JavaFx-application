package Controller;

import Adapter.DatabaseAdapter;
import Adapter.PaymentServiceAdapter;

import BidlyCore.Antique;
import BidlyCore.Application;
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
import java.util.ArrayList;

public class BuyerController extends Controller {

    // Adapter
    DatabaseAdapter databaseAdapter = new DatabaseAdapter("database.sqlite");
    PaymentServiceAdapter paymentService = new PaymentServiceAdapter();

    // Globals
    private int ANTIQUE_ID;
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
    private VBox paymentSuccessMessageContainer;
    @FXML
    private VBox paymentFailureMessageContainer;

    public void initialize() throws Exception {
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
        Label successLabel = new Label("Payment succeeded and bid placed.");
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
        Label failureLabel = new Label("Payment failed and bid did not register.");
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
        ArrayList<Antique> products = this.databaseAdapter.getAllProducts();

        HBox row = createListRow();
        for ( int i = 0; i < products.size(); i++ ) {
            if ( i % 3 == 0 && i != 0 ) {
                row.getChildren().add( createProductView( products.get(i) ) );
                productListingContainer.getChildren().add(row);
                row = createListRow();
            } else {
                row.getChildren().add( createProductView( products.get(i) ) );
            }
        }
        productListingContainer.getChildren().add(row);

    }

    @FXML
    private void bidClick( int antiqe_id, int current_bid_price, int bid_amount, Label bid_message, Label current_bid_output ) throws Exception {

        int bids = databaseAdapter.getAmountOfBidsForAntique( antiqe_id );

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
                this.ANTIQUE_ID = antiqe_id;
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

            // Find the highest bid for the selected product //
            int highest_bid = databaseAdapter.getHighestBidOfAntique(antiqe_id);

            // If the current bid is higher then the previous bid, process the bid further
            if ( bid_amount > highest_bid ) {

                // Toggle payment popup
                paymentContainer.setVisible(true);
                paymentContainer.setManaged(true);
                // Toggle product overview container
                productOverviewContainer.setVisible(false);
                productOverviewContainer.setManaged(false);

                // Send the product data to the global data variables
                this.ANTIQUE_ID = antiqe_id;
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
    private void payButtonClick() throws Exception {

        // Check if the payment information is correct
        if ( paymentService.processPayment( cardNumber.getText(), expirationDate.getText(), securityCode.getText() )) {
            // Show and hide containers
            paymentContainer.setVisible(false);
            paymentContainer.setManaged(false);
            paymentSuccessMessageContainer.setVisible(true);
            paymentSuccessMessageContainer.setManaged(true);
            // Update the bid in the product overview
            CURRENT_BID_OUTPUT.setText(String.valueOf(BID_AMOUNT)+"kr");
            // Insert the bid into the databaseAdapter system
            databaseAdapter.insertBid( BID_AMOUNT, ANTIQUE_ID);
            // Display bid succeeded message
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
        this.changeView(actionEvent,"authenticationView.fxml", 500, 500 );
    }

    private HBox createListRow() {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER);
        row.setStyle("-fx-background-color:white");
        return row;
    }

    private VBox createProductView( Antique antique) {

        // Create main container
        VBox container = new VBox();
        container.setStyle("-fx-border-color:rgba(0,0,0,0.2);-fx-border-radius:10;-fx-background-color:rgba(0,0,0,0.1)");
        container.setSpacing(5);
        container.setMinHeight(250);
        container.setMinWidth(180);
        container.setAlignment(Pos.CENTER);

        // Label for store name
        Label storename = new Label( antique.getStoreName() );
        storename.setStyle("-fx-font-size:10");

        // Label for product name
        Label name = new Label( antique.getName() );
        name.setStyle("-fx-font-size:12.5;-fx-font-weight:bold");

        // ImageView for image of product
        Image image_object = new Image( antique.getPic_url() ,true);
        ImageView image = new ImageView(image_object);
        image.setFitHeight(75);
        image.setFitWidth(75);

        // Label for description
        Label description = new Label(antique.getDescription() );
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
        if ( antique.getLast_bid_price() != 0 ) {
            bid_text.setText( "Last bid: " );
            bid_amount.setText(String.valueOf(antique.getLast_bid_price()) + "kr");
        } else {
            bid_text.setText( "Starting bid price: " );
            bid_amount.setText(String.valueOf( antique.getPrice() ) + "kr");
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
        bid_field.setPromptText("X kr");
        bid_field.setMaxWidth(75);

        bid_button.setOnAction(e -> {
            try {
                int bid_amount_converted = Integer.parseInt( bid_field.getText() );

                if ( antique.getLast_bid_price() != 0 ) {
                    this.bidClick( antique.getAntique_id(), antique.getLast_bid_price(), bid_amount_converted, message_output, bid_amount );
                } else {
                    this.bidClick( antique.getAntique_id(), antique.getPrice(), bid_amount_converted, message_output, bid_amount );
                }

            } catch (Exception exception ) {
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
