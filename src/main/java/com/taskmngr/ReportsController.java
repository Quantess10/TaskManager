package com.taskmngr;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportsController {
    private ObservableList<TeamMember> teamMembers = FXCollections.observableArrayList();
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    private Gson gson = new Gson();
    private static final String MEMBERS_FILE = "team_members.json";
    private static final String TASKS_FILE = "tasks.json";

    @FXML
    private ChoiceBox<String> workerChoice;
    @FXML
    private TableColumn<TeamMember, String> taskColumn;
    @FXML
    private TableColumn<TeamMember, String> startDateColumn;
    @FXML
    private TableColumn<TeamMember, String> finishDateColumn;
    @FXML
    private TableColumn<TeamMember, String> statusColumn;
    @FXML
    private TableColumn<Task, Number> countDaysColumn;
    @FXML
    private TableView<Task> reportsTable;

    @FXML
    private void initialize() {
        loadTeamMembers();
        loadTasks();
        populateChoiceBox();

        countDaysColumn.setCellValueFactory(new PropertyValueFactory<>("countDays"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        finishDateColumn.setCellValueFactory(new PropertyValueFactory<>("finishDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        countDaysColumn
                .setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDaysBetween()));

        workerChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateTasksTable(newSelection);
            }
        });

        reportsTable.setItems(tasks);
    }

    private void loadTasks() {
        try (Reader reader = new InputStreamReader(new FileInputStream(TASKS_FILE), StandardCharsets.UTF_8)) {
            Type listOfTeamMembersType = new TypeToken<List<Task>>() {
            }.getType();
            List<Task> tasks1 = gson.fromJson(reader, listOfTeamMembersType);
            tasks.setAll(tasks1);
        } catch (IOException e) {
            System.out.println("Nie udało się wczytać danych: " + e.getMessage());
        }
    }

    private void loadTeamMembers() {
        try (Reader reader = new InputStreamReader(new FileInputStream(MEMBERS_FILE), StandardCharsets.UTF_8)) {
            Type teamMemberListType = new TypeToken<List<TeamMember>>() {
            }.getType();
            List<TeamMember> members = gson.fromJson(reader, teamMemberListType);
            teamMembers.addAll(members);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateChoiceBox() {
        workerChoice.getItems().clear();
        for (TeamMember member : teamMembers) {
            workerChoice.getItems().add(member.getFullName());
        }
    }

    private void updateTasksTable(String employeeName) {
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getWorker().equals(employeeName))
                .collect(Collectors.toList());
        reportsTable.setItems(FXCollections.observableArrayList(filteredTasks));
    }
}
