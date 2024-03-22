module com.taskmngr {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.taskmngr to javafx.fxml;
    exports com.taskmngr;
}
