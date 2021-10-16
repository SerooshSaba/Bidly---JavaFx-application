module com.bidly.bidly {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.bidly.Core to javafx.fxml;
    exports com.bidly.Core;
    exports com.bidly.Core.Controller;
    opens com.bidly.Core.Controller to javafx.fxml;
}