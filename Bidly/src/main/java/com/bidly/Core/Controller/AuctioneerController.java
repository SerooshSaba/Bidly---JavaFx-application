package com.bidly.Core.Controller;

import Adapter.DatabaseAdapter;

import com.bidly.Core.Utility.DataValidator;
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
import java.sql.ResultSet;
import java.sql.SQLException;

// TODO Skap en oversikt over antall produkter det er i butikken
// TODO Oversikt over hvor mye total bids som er på produktene
// TODO Lag en edit account seksjon

public class AuctioneerController extends Controller {

    // - - - Store title label
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

    DatabaseAdapter databaseAdapter = new DatabaseAdapter();
    DataValidator validator = new DataValidator();

    public void initialize() throws SQLException {
        // Load storename
        databaseAdapter.statement("SELECT name FROM stores WHERE store_id = ?");
        databaseAdapter.passInt(1);
        storeName.setText(databaseAdapter.getResult().getString("name"));
        databaseAdapter.close();
        // Load store owner products
        this.loadProducts();
    }

    private void loadProducts() throws SQLException {
        // Empty container
        ProductContainer.getChildren().clear();
        // Get all product from store
        databaseAdapter.statement("SELECT * FROM antiqes WHERE store_id = ?");
        databaseAdapter.passInt(1);
        ResultSet result = databaseAdapter.getResult();
        // Print out all products
        while( result.next() ) {
            int    id   = result.getInt("antiqe_id");
            String name = result.getString("name");
            String picurl = result.getString("pic_url");
            String description = result.getString("description");
            int price = result.getInt("price");
            ProductContainer.getChildren().add( this.createProductListItem(id,name,picurl,description,price) );
        }
        databaseAdapter.close();
    }

    // Add item method
    @FXML
    protected void addItemClick(ActionEvent event) throws SQLException {
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

            int price = Integer.parseInt(input[3].replaceAll("\\s+", ""));
            databaseAdapter.statement("INSERT INTO antiqes VALUES ( NULL, ?, ?, ?, ?, ? )");
            databaseAdapter.passString(input[0]);
            databaseAdapter.passString(input[1]);
            databaseAdapter.passString(input[2]);
            databaseAdapter.passInt(price);
            databaseAdapter.passInt(1);

            if ( databaseAdapter.execute() ) {
                MessageLabel.setStyle("-fx-text-fill:green");
                MessageLabel.setText("Product inserted to store!");
                loadProducts();
            }
            databaseAdapter.close();
            // Empty form
            ItemName.setText("");
            ItemDescription.setText("");
            ItemPicUrl.setText("");
            ItemPrice.setText("");
        }
    }

    @FXML
    protected void logoutClick(ActionEvent actionEvent) throws IOException {
        this.changeView(actionEvent,"authenticationView.fxml");
    }

    @FXML
    // Add item method
    public void deleteClick(ActionEvent actionEvent) throws SQLException {
        Button button = (Button)(actionEvent.getSource());
        this.databaseAdapter.statement("DELETE FROM antiqes WHERE antiqe_id = ?");
        this.databaseAdapter.passInt(Integer.parseInt(button.getId()));
        this.databaseAdapter.execute();
        this.databaseAdapter.close();
        this.loadProducts();
    }

    // Product list item creator
    public HBox createProductListItem(int id, String name, String picurl, String description, int price ) {

        System.out.println(picurl);

        HBox container = new HBox();
        container.setPrefWidth(280);
        container.setSpacing(5);

        VBox left_image_container = new VBox();

        Image img_obj = new Image(picurl,true);
        ImageView image = new ImageView(img_obj);
        image.setFitHeight(50);
        image.setFitWidth(50);
        left_image_container.getChildren().add(image);

        VBox right_container = new VBox();

        // Labels
        Label name_label = new Label();
        name_label.setText(name);
        name_label.setStyle("-fx-font-size:12.5;-fx-font-weight:bold");

        Label price_label = new Label();
        price_label.setText(price + "$");
        price_label.setStyle("-fx-text-fill:green;-fx-font-size:10");
        // Delete button
        Button delete_button = new Button();
        delete_button.setText("Delete");
        delete_button.setId(String.valueOf(id));
        delete_button.setPadding(new Insets(1));
        delete_button.setOnAction(e -> {
            try {
                this.deleteClick(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        right_container.getChildren().addAll(name_label,price_label,delete_button);
        container.getChildren().addAll(left_image_container,right_container);

        return container;
    }

}
