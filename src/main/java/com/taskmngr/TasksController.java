package com.taskmngr;

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

public class TasksController {
    private ObservableList<TeamMember> teamMembers = FXCollections.observableArrayList();
    private ObservableList<Task> tasks = FXCollections.observableArrayList();
    FilteredList<Task> filteredTasks = new FilteredList<>(tasks, task -> !task.getStatus().equals("Z"));
    private Gson gson = new Gson();
    private static final String MEMBERS_FILE = "team_members.json";
    private static final String TASKS_FILE = "tasks.json";

    @FXML
    private TextField addTaskField;
    @FXML
    private TextField addDescriptionField;
    @FXML
    private ChoiceBox<String> setWorkerChoice;
    @FXML
    private TableView<Task> tasksTable;
    @FXML
    private TableColumn<Task, String> taskColumn;
    @FXML
    private TableColumn<Task, String> descriptionColumn;
    @FXML
    private TableColumn<Task, String> workerColumn;
    @FXML
    private TableColumn<Task, String> startDateColumn;
    @FXML
    private TableColumn<Task, String> statusColumn;

    @FXML
    private void initialize() {
        loadTeamMembers();
        loadTasks();
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        workerColumn.setCellValueFactory(new PropertyValueFactory<>("worker"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        tasksTable.setItems(filteredTasks);
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

    @FXML
    private void updateTaskStatus() {
        Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null && "N".equals(selectedTask.getStatus())) {
            selectedTask.setStatus("W");
            saveTasks();
            tasksTable.refresh();
            System.out.println("Status zadania zaktualizowany na 'W trakcie'.");
        } else {
            System.out.println("Wybierz zadanie niezrealizowane do aktualizacji statusu.");
        }
    }

    @FXML
    private void finishTaskStatus() {
        Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null && "W".equals(selectedTask.getStatus())) {
            selectedTask.setStatus("Z");
            selectedTask.setFinishDate(LocalDate.now());
            saveTasks();
            tasksTable.refresh();
            filteredTasks.setPredicate(task -> !task.getStatus().equals("Z"));
            System.out.println("Status zadania zaktualizowany na 'Zakończone'.");
        } else {
            System.out.println("Najpierw rozpocznij zadanie, aby móc je ukończyć.");
        }
    }

    private void saveTasks() {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(TASKS_FILE), StandardCharsets.UTF_8)) {
            gson.toJson(new ArrayList<>(tasks), writer);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Nie udało się zapisać zadań: " + e.getMessage());
        }
    }
}
