package com.taskmngr;

import java.io.IOException;
import javafx.fxml.FXML;

public class SideMenuController {

    @FXML
    private void switchToTasks() throws IOException {
        App.setRoot("tasks");
    }

    @FXML
    private void switchToDoneTasks() throws IOException {
        App.setRoot("doneTasks");
    }

    @FXML
    private void switchToManageTasks() throws IOException {
        App.setRoot("manageTasks");
    }

    @FXML
    private void switchToManageTeam() throws IOException {
        App.setRoot("manageTeam");
    }

    @FXML
    private void switchToReports() throws IOException {
        App.setRoot("reports");
    }

}
