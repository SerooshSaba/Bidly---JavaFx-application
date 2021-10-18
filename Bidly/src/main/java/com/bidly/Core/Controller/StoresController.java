package com.bidly.Core.Controller;

import Adapter.DatabaseAdapter;
import com.bidly.Core.Model.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class StoresController extends Controller {

    DatabaseAdapter database = new DatabaseAdapter();

    @FXML
    private VBox storeListingContainer;

    public void initialize() throws SQLException {
        this.loadStores();
    }

    private void loadStores() throws SQLException {
        // Empty container
        storeListingContainer.getChildren().clear();
        // Get all stores
        ArrayList<Store> stores = database.getStores();
        for ( Store store : stores ) {
            storeListingContainer.getChildren().add( this.storeListingContainer( store.getStore_id(), store.getName() ) );
        }
    }

    @FXML
    // Delete Store
    public void deleteClick(ActionEvent actionEvent) throws SQLException {
        Button button = (Button)(actionEvent.getSource());
        database.deleteStore(Integer.valueOf(button.getId()));
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        // Edit button
        Button edit_button = new Button();
        edit_button.setText("Edit");

        name_container.getChildren().addAll(name_label, delete_button, edit_button);
        container.getChildren().addAll(name_container);

        return container;
    }

    @FXML
    protected void backClick(ActionEvent event) throws IOException {
        this.changeView( event,"adminView.fxml",750,500 );
    }
}
