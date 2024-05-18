module com.taskmngr {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.taskmngr to javafx.fxml, com.google.gson;

    exports com.taskmngr;
}
