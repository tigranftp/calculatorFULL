module com.example.calculatorfull {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.calculatorfull to javafx.fxml;
    exports com.example.calculatorfull;
}