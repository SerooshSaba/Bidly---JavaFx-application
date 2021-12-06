package Controller;

import BidlyCore.Application;
import BidlyCore.Store;
import Repositories.AntiqueRepository;
import Repositories.StoreRepository;
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

    // Stores list
    @FXML
    private VBox storeListingContainer;

    private AntiqueRepository antiqueRepository = new AntiqueRepository( "database.sqlite" );
    private StoreRepository storeRepository = new StoreRepository( "database.sqlite" );

    public void initialize() {

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
        int stores = storeRepository.getAmountOfStores();
        if ( stores == 1 ) {
            StoreAmount.setText( String.valueOf(stores) + " Seller" );
        } else {
            StoreAmount.setText( String.valueOf(stores) + " Sellers" );
        }

        int antiques = antiqueRepository.getAmountOfAntiques();

        if ( antiques == 1 ) {
            ProductAmount.setText( antiques + " Product" );
        } else {
            ProductAmount.setText( antiques + " Products" );
        }

        int bids = antiqueRepository.getAllBidsAmount();

        if ( bids == 1 ) {
            BidAmount.setText( bids + " Bid" );
        } else {
            BidAmount.setText( bids + " Bids" );
        }

        this.loadStores();
    }


    public HBox storeListingContainer(int id, String name) {
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

    private void loadStores() {
        storeListingContainer.getChildren().clear();
        ArrayList<Store> stores = storeRepository.getStores();
        for ( Store store : stores) {
            storeListingContainer.getChildren().add( this.storeListingContainer( store.getStore_id(), store.getName() ) );
        }
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        Button button = (Button)(actionEvent.getSource());
        storeRepository.deleteStore(Integer.valueOf(button.getId()));
        this.loadStores();
    }

}
