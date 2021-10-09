package com.bidly.bidly;

import Components.ComponentCreator;
import Utility.DataValidator;
import Utility.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreOwnerController {


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

    Database database = new Database();
    DataValidator validator = new DataValidator();
    ComponentCreator creator = new ComponentCreator(this);

    public void initialize() throws SQLException {
        // Load storename
        database.statement("SELECT name FROM stores WHERE store_id = ?");
        database.passInt(1);
        storeName.setText(database.getResult().getString("name"));
        database.close();
        // Load store owner products
        this.loadProducts();
    }

    private void loadProducts() throws SQLException {
        // Empty container
        ProductContainer.getChildren().clear();

        // Get all product from store
        database.statement("SELECT * FROM antiqes WHERE store_id = ?");
        database.passInt(1);
        ResultSet result = database.getResult();

        // Print out all products
        while( result.next() ) {
            int    id   = result.getInt("antiqe_id");
            String name = result.getString("name");
            String picurl = result.getString("pic_url");
            String description = result.getString("description");
            int price = result.getInt("price");
            ProductContainer.getChildren().add( creator.createProductListItem(id,name,picurl,description,price) );
        }

        database.close();
    }

    // Add item method
    @FXML
    protected void addItemClick(ActionEvent event) throws IOException, SQLException {

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

            database.statement("INSERT INTO antiqes VALUES ( NULL, ?, ?, ?, ?, ? )");
            database.passString(input[0]);
            database.passString(input[1]);
            database.passString(input[2]);
            database.passInt(price);
            database.passInt(1);

            if ( database.execute() ) {
                MessageLabel.setStyle("-fx-text-fill:green");
                MessageLabel.setText("Product inserted to store!");
                loadProducts();
            }

            database.close();

            // Empty form
            ItemName.setText("");
            ItemDescription.setText("");
            ItemPicUrl.setText("");
            ItemPrice.setText("");

        }
    }

    // Add item method
    public void deleteClick(ActionEvent actionEvent) throws SQLException {
        Button button = (Button)(actionEvent.getSource());
        /*
        this.database.statement("DELETE FROM antiqes WHERE antiqe_id = ?");
        this.database.passInt(Integer.parseInt(button.getId()));
        this.database.execute();
        this.database.close();
        this.loadProducts();
        */
    }

    /* REDIRECT
    FXMLLoader root = new FXMLLoader(Application.class.getResource("authentication.fxml"));
    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root.load(), 750, 500);
    stage.setScene(scene);
    stage.show();
    ApplicationController controller = root.getController();
    controller.shout();
    */


}
