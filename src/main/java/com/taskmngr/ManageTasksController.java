package com.taskmngr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;
import java.util.List;

public class ManageTasksController {
    private ObservableList<TeamMember> teamMembers = FXCollections.observableArrayList();
    private ObservableList<Task> tasks = FXCollections.observableArrayList();
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
        populateChoiceBox();
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        workerColumn.setCellValueFactory(new PropertyValueFactory<>("worker"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        tasksTable.setItems(tasks);
    }

    private void saveTasks() {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(TASKS_FILE), StandardCharsets.UTF_8)) {
            gson.toJson(new ArrayList<>(tasks), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        setWorkerChoice.getItems().clear();
        for (TeamMember member : teamMembers) {
            setWorkerChoice.getItems().add(member.getFullName());
        }
    }

    @FXML
    private void addTask() {
        String taskName = addTaskField.getText();
        String description = addDescriptionField.getText();
        String selectedWorker = setWorkerChoice.getValue();

        if (taskName.isEmpty() || description.isEmpty() || selectedWorker == null) {
            System.out.println("Wszystkie pola muszą być wypełnione!");
            return;
        }

        Task newTask = new Task(taskName, description, selectedWorker);
        tasks.add(newTask);
        saveTasks(); // Teraz zapiszemy pełne dane do pliku JSON
        System.out.println("Dodano zadanie dla: " + selectedWorker);
        // Czyść pola po dodaniu zadania
        addTaskField.clear();
        addDescriptionField.clear();
        setWorkerChoice.getSelectionModel().clearSelection();
    }

    @FXML
    private void editTask() {
        Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            addTaskField.setText(selectedTask.getTask());
            addDescriptionField.setText(selectedTask.getDescription());
            setWorkerChoice.setValue(selectedTask.getWorker());
            tasksTable.getItems().remove(selectedTask);
        } else {
            System.out.println("Wybierz zadanie do edycji.");
        }
    }

    @FXML
    private void deleteTask() {
        int selectedIndex = tasksTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Task selectedTask = tasksTable.getItems().get(selectedIndex);
            tasksTable.getItems().remove(selectedIndex);
            saveTasks();
            System.out.println("Usunięto zadanie: " + selectedTask);
        } else {
            System.out.println("Zaznacz zadanie do usunięcia.");
        }
    }

}
