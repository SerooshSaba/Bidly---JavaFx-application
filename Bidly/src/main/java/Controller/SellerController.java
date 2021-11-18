package Controller;

import Adapter.DatabaseAdapter;

import Adapter.ValidatorAdapter;
import BidlyCore.Antique;
import BidlyCore.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuctioneerController extends Controller {

    // GLOBAL VARIABLE
    private int STORE_ID = 0;

    @FXML
    private Button logoutButton;

    @FXML
    private VBox LoginRegisterView;

    @FXML
    private Label registerErrorMessage;

    @FXML
    private VBox storeList;

    @FXML
    private TextField registerStoreName;

    @FXML
    private VBox AuctioneerView;

    @FXML
    private Label storeName;

    // Form container
    @FXML
    private VBox AddForm;
    @FXML
    private Label MessageLabel;

    // Add item form fields
    @FXML
    private TextField ItemName;
    @FXML
    private TextField ItemDescription;
    @FXML
    private TextField ItemPicUrl;
    @FXML
    private TextField ItemPrice;

    // Product view container
    @FXML
    private VBox ProductContainer;

    // Delete store button
    @FXML
    private Label deleteStoreLabel;

    DatabaseAdapter databaseAdapter = new DatabaseAdapter("database.sqlite");
    ValidatorAdapter validator = new ValidatorAdapter();

    public void initialize() throws Exception {
        ArrayList<Store> stores = databaseAdapter.getStores();
        for ( Store store : stores ) {
            Button button = new Button();
            button.setText( store.getName() );
            button.setOnAction(e -> {
                this.STORE_ID = store.getStore_id();
                try {
                    login();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            storeList.getChildren().add( button );
        }
    }

    @FXML
    private void login() throws Exception {
        storeName.setText(databaseAdapter.getStoreName(this.STORE_ID));
        this.loadProducts();
        this.LoginRegisterView.setManaged(false);
        this.LoginRegisterView.setVisible(false);
        this.AuctioneerView.setManaged(true);
        this.AuctioneerView.setVisible(true);
        logoutButton.setText("LOGOUT");
    }

    @FXML
    private void registerStore() throws Exception {
        String storename = registerStoreName.getText();
        if ( validator.stringEmpty(storename) ) {
            registerErrorMessage.setText("Field cannot be empty");
        } else {
            Store store = new Store( 0, storename );
            databaseAdapter.insertStore(store);
            ArrayList<Store> stores = databaseAdapter.getStores();
            STORE_ID = stores.get(stores.size()-1).getStore_id();
            this.login();
        }
    }

    private void loadProducts() throws Exception {
        this.ProductContainer.getChildren().clear();
        ArrayList<Antique> antiques = databaseAdapter.getStoreProducts(STORE_ID);
        for ( Antique antique : antiques) {
            ProductContainer.getChildren().add( this.createProductListItem(antique) );
        }
    }

    // Add item method
    @FXML
    protected void addItemClick(ActionEvent event) throws Exception {
        String[] input = new String[]{ this.ItemName.getText(), this.ItemDescription.getText(), this.ItemPicUrl.getText(), this.ItemPrice.getText() };
        // Validate inputs
        if ( validator.stringsEmpty(input) ) {
            MessageLabel.setStyle("-fx-text-fill: red");
            MessageLabel.setText("Fill out every input field of form.");
        }
        else if ( !validator.containsNumber(input[3]) ) { // Price field is a number
            MessageLabel.setStyle("-fx-text-fill: red");
            MessageLabel.setText("Price field must be a number");
        }
        else if ( !validator.isUrl(input[2]) ) {
            MessageLabel.setStyle("-fx-text-fill: red");
            MessageLabel.setText("Image link is not valid");
        }
        else { // If everything is validated, process the form

            Antique antique = new Antique( input[0], input[1], input[2], Integer.parseInt(input[3]), STORE_ID );

            if ( databaseAdapter.insertAntiqe(antique) == 1 ) {
                MessageLabel.setStyle("-fx-text-fill:green");
                MessageLabel.setText("Product inserted to store!");
                loadProducts();
            }

            // Empty form
            ItemName.setText("");
            ItemDescription.setText("");
            ItemPicUrl.setText("");
            ItemPrice.setText("");
        }
    }

    @FXML
    protected void logoutClick(ActionEvent actionEvent) throws IOException {
        this.changeView(actionEvent,"authenticationView.fxml", 500, 500 );
    }

    @FXML
    // Add item method
    public void deleteClick(ActionEvent actionEvent) throws Exception {
        Button button = (Button)(actionEvent.getSource());
        this.databaseAdapter.deleteAntiqe(button.getId());
        this.loadProducts();
    }

    @FXML
    public void deleteStore(ActionEvent actionEvent) throws SQLException {
        databaseAdapter.deleteStore(STORE_ID);
        deleteStoreLabel.setVisible(true);
        deleteStoreLabel.setManaged(true);
    }

    // Product list item creator
    public HBox createProductListItem( Antique antique) {

        HBox container = new HBox();
        container.setPrefWidth(280);
        container.setSpacing(5);

        VBox left_image_container = new VBox();

        Image img_obj = new Image( antique.getPic_url() ,true);
        ImageView image = new ImageView(img_obj);
        image.setFitHeight(50);
        image.setFitWidth(50);
        left_image_container.getChildren().add(image);

        VBox right_container = new VBox();

        // Labels
        Label name_label = new Label();
        name_label.setText( antique.getName() );
        name_label.setStyle("-fx-font-size:12.5;-fx-font-weight:bold");

        Label price_label = new Label();
        price_label.setText( antique.getPrice() + "$");
        price_label.setStyle("-fx-text-fill:green;-fx-font-size:10");
        // Delete button
        Button delete_button = new Button();
        delete_button.setText("Delete");
        delete_button.setId(String.valueOf( antique.getAntique_id() ));
        delete_button.setPadding(new Insets(1));
        delete_button.setOnAction(e -> {
            try {
                this.deleteClick(e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        right_container.getChildren().addAll(name_label,price_label,delete_button);
        container.getChildren().addAll(left_image_container,right_container);

        return container;
    }

}
