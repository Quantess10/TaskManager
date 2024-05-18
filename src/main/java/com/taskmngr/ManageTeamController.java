package com.taskmngr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ManageTeamController {
    private ObservableList<TeamMember> teamMembers = FXCollections.observableArrayList();
    private static final String JSON_FILE = "team_members.json";
    private Gson gson = new Gson();

    @FXML
    private TextField addFirstNameField;
    @FXML
    private TextField addLastNameField;
    @FXML
    private TextField addPositionField;
    @FXML
    private TableView<TeamMember> teamTable;
    @FXML
    private TableColumn<TeamMember, String> firstNameColumn;
    @FXML
    private TableColumn<TeamMember, String> lastNameColumn;
    @FXML
    private TableColumn<TeamMember, String> positionColumn;

    @FXML
    private void initialize() {
        loadTeamMembers();
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        teamTable.setItems(teamMembers);
    }

    private void saveTeamMembers() {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(new ArrayList<>(teamMembers), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTeamMembers() {
        try (FileReader reader = new FileReader(JSON_FILE)) {
            Type listOfTeamMembersType = new TypeToken<List<TeamMember>>() {
            }.getType();
            List<TeamMember> members = gson.fromJson(reader, listOfTeamMembersType);
            teamMembers.setAll(members);
        } catch (IOException e) {
            System.out.println("Nie udało się wczytać danych: " + e.getMessage());
        }
    }

    @FXML
    private void addMember() {
        String firstName = addFirstNameField.getText().trim();
        String lastName = addLastNameField.getText().trim();
        String position = addPositionField.getText().trim();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !position.isEmpty()) {
            TeamMember newMember = new TeamMember(firstName, lastName, position);
            teamMembers.add(newMember);
            saveTeamMembers();
            addFirstNameField.clear();
            addLastNameField.clear();
            addPositionField.clear();
            System.out.println("Członek zespołu dodany: " + newMember.toString());
        } else {
            System.out.println("Wszystkie pola muszą być wypełnione!");
        }
    }

    @FXML
    private void deleteMember() {
        System.out.println("usuwam");
    }

}
