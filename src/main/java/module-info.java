module com.example.paint {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;
    requires javafx.swing;
    requires org.testng;


    opens com.example.paint to javafx.fxml;
    exports com.example.paint;
}