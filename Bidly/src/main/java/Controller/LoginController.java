package Controller;

import BidlyCore.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController extends Controller {

    @FXML
    private ImageView logoImage;
    @FXML
    private ComboBox<String> userTypes;

    public void initialize(){
        Image image = new Image(String.valueOf(Application.class.getResource("/images/logo.png")));
        this.logoImage.setImage(image);
        this.logoImage.setPreserveRatio(true);
        this.logoImage.setFitWidth(90);
        this.userTypes.getItems().setAll("Admin", "Seller", "Buyer");
    }

    @FXML
    protected void LoginClick(ActionEvent event) {
        String UserSelection = userTypes.getSelectionModel().selectedItemProperty().getValue();
        if (  UserSelection.equals("Admin")  ) {
            this.changeView( event,"adminView.fxml",  850, 750 );
        }
        else if ( UserSelection.equals("Seller") ) {
            this.changeView( event,"sellerView.fxml", 850, 750 );
        }
        else {
            this.changeView( event,"buyerView.fxml",  850, 750 );
        }
    }

}