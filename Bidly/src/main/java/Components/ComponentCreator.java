package Components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.bidly.bidly.StoreOwnerController;
import java.sql.SQLException;

public class ComponentCreator {


    private StoreOwnerController controller;

    public ComponentCreator( StoreOwnerController controller ){
        this.controller = controller;
    }

    public HBox createProductListItem(int id, String name, String picurl, String description, int price ) {

        HBox container = new HBox();
        //container.setStyle("-fx-border-color:rgb(200,200,200)");
        container.setPrefWidth(280);
        container.setSpacing(5);

        VBox left_image_container = new VBox();

        Image img_obj = new Image(picurl);
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
        /*
        delete_button.setOnAction(e -> {
            try {
                this.controller.deleteClick(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        */

        right_container.getChildren().addAll(name_label,price_label,delete_button);
        container.getChildren().addAll(left_image_container,right_container);

        return container;
    }


}
