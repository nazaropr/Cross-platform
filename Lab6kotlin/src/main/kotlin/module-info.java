module com.example.lab6 {
    requires javafx.controls;
    requires javafx.fxml;
                requires kotlin.stdlib;
    requires java.sql;


    opens com.example.lab6 to javafx.fxml;
    exports com.example.lab6;
}