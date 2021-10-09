module com.bidly.bidly {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.bidly.bidly to javafx.fxml;
    exports com.bidly.bidly;
}