package com.bidly.Core.Controller;

import Adapter.DatabaseAdapter;

import com.bidly.Core.Application;
import com.bidly.Core.Model.Auctioneer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class AdminController extends Controller {

    // Images
    @FXML
    private ImageView StoreIcon;
    @FXML
    private ImageView ProductIcon;
    @FXML
    private ImageView BidIcon;

    // Platform data
    @FXML
    private Label StoreAmount;
    @FXML
    private Label ProductAmount;
    @FXML
    private Label BidAmount;

    // Auctioneer overview
    @FXML
    private VBox storeListingContainer;

    DatabaseAdapter databaseAdapter = new DatabaseAdapter("database.sqlite");

    public void initialize() throws Exception {

        // Load images
        Image store_jpg = new Image(String.valueOf(Application.class.getResource("/images/store.PNG")));
        StoreIcon.setFitHeight(30);
        StoreIcon.setFitWidth(30);
        StoreIcon.setImage(store_jpg);

        Image product_jpg = new Image(String.valueOf(Application.class.getResource("/images/box.PNG")));
        ProductIcon.setFitHeight(35);
        ProductIcon.setFitWidth(35);
        ProductIcon.setImage(product_jpg);

        Image bid_jpg = new Image(String.valueOf(Application.class.getResource("/images/bid.JPG")));
        BidIcon.setFitHeight(35);
        BidIcon.setFitWidth(35);
        BidIcon.setImage(bid_jpg);

        // Load platform data
        int stores = databaseAdapter.getAmountOfStores();
        if ( stores == 1 ) {
            StoreAmount.setText( String.valueOf(stores) + " Auctioneer" );
        } else {
            StoreAmount.setText( String.valueOf(stores) + " Stores" );
        }

        int products = databaseAdapter.getAmountOfProducts();
        if ( products == 1 ) {
            ProductAmount.setText( products + " Product" );
        } else {
            ProductAmount.setText( products + " Products" );
        }

        int bids = databaseAdapter.getAllBidsAmount();
        if ( bids == 1 ) {
            BidAmount.setText( bids + " Bid" );
        } else {
            BidAmount.setText( bids + " Bids" );
        }

        this.loadStores();
    }


    public HBox storeListingContainer(int id, String name) throws Exception {
        HBox container = new HBox();
        container.setPrefWidth(250);
        container.setSpacing(10);
        container.setAlignment(Pos.CENTER);

        VBox name_container = new VBox();
        name_container.setAlignment(Pos.CENTER);

        // Labels
        Label name_label = new Label();
        name_label.setText(name);
        name_label.setStyle("-fx-font-size:14;-fx-font-weight:bold");

        // Delete button
        Button delete_button = new Button();
        delete_button.setText("Delete");
        delete_button.setId(String.valueOf(id));
        delete_button.setPadding(new Insets(1));
        delete_button.setOnAction(e -> {
            try {
                this.deleteClick(e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        name_container.getChildren().addAll( name_label, delete_button );
        container.getChildren().addAll(name_container);

        return container;
    }

    private void loadStores() throws Exception {
        storeListingContainer.getChildren().clear();
        ArrayList<Auctioneer> auctioneers = databaseAdapter.getStores();
        for ( Auctioneer auctioneer : auctioneers) {
            storeListingContainer.getChildren().add( this.storeListingContainer( auctioneer.getAuctioneer_id(), auctioneer.getName() ) );
        }
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) throws Exception {
        Button button = (Button)(actionEvent.getSource());
        databaseAdapter.deleteStore(Integer.valueOf(button.getId()));
        this.loadStores();
    }


    @FXML
    protected void logoutClick(ActionEvent event) throws IOException {
        this.changeView(event,"authenticationView.fxml", 750, 500 );
    }

}
