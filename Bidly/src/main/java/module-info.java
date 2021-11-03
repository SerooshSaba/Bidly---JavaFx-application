module com.BidlyCore {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens BidlyCore to javafx.fxml;
    exports BidlyCore;
    exports Controller;
    opens Controller to javafx.fxml;
}
