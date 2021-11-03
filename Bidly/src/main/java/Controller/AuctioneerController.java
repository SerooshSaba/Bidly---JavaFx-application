package Controller;

import Adapter.DatabaseAdapter;

import Adapter.ValidatorAdapter;
import BidlyCore.Antiqe;
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
import java.util.ArrayList;

public class AuctioneerController extends Controller {

    // - - - Auctioneer title label
    @FXML
    private Label storeName;

    // - - - Form container
    @FXML
    private VBox AddForm;
    @FXML
    private Label MessageLabel;

    // Input fields
    @FXML
    private TextField ItemName;
    @FXML
    private TextField ItemDescription;
    @FXML
    private TextField ItemPicUrl;
    @FXML
    private TextField ItemPrice;

    // - - - Product view continer
    @FXML
    private VBox ProductContainer;

    DatabaseAdapter databaseAdapter = new DatabaseAdapter("database.sqlite");
    ValidatorAdapter validator = new ValidatorAdapter();

    public void initialize() throws Exception {
        // Load name and products of store with store_id = 1
        storeName.setText(databaseAdapter.getStoreName(1));
        this.loadProducts(1);
    }

    private void loadProducts( int store_id ) throws Exception {
        this.ProductContainer.getChildren().clear();
        ArrayList<Antiqe> antiqes = databaseAdapter.getStoreProducts(store_id);
        for ( Antiqe antiqe : antiqes ) {
            ProductContainer.getChildren().add( this.createProductListItem( antiqe ) );
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

            Antiqe antiqe = new Antiqe( input[0], input[1], input[2], Integer.parseInt(input[3]), 1 );

            if ( databaseAdapter.insertAntiqe( antiqe ) == 1 ) {
                MessageLabel.setStyle("-fx-text-fill:green");
                MessageLabel.setText("Product inserted to store!");
                loadProducts(1);
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
        this.loadProducts(1);
    }

    // Product list item creator
    public HBox createProductListItem( Antiqe antiqe ) {

        HBox container = new HBox();
        container.setPrefWidth(280);
        container.setSpacing(5);

        VBox left_image_container = new VBox();

        Image img_obj = new Image( antiqe.getPic_url() ,true);
        ImageView image = new ImageView(img_obj);
        image.setFitHeight(50);
        image.setFitWidth(50);
        left_image_container.getChildren().add(image);

        VBox right_container = new VBox();

        // Labels
        Label name_label = new Label();
        name_label.setText( antiqe.getName() );
        name_label.setStyle("-fx-font-size:12.5;-fx-font-weight:bold");

        Label price_label = new Label();
        price_label.setText( antiqe.getPrice() + "$");
        price_label.setStyle("-fx-text-fill:green;-fx-font-size:10");
        // Delete button
        Button delete_button = new Button();
        delete_button.setText("Delete");
        delete_button.setId(String.valueOf( antiqe.getAntiqe_id() ));
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
