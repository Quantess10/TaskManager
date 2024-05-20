package com.taskmngr;

import java.time.LocalDate;

public class Task {
    private String task;
    private String description;
    private String worker;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String status;

    public Task(String task, String description, String worker) {
        this.task = task;
        this.description = description;
        this.worker = worker;
        this.startDate = LocalDate.now();
        this.status = "N";
    }

    // Gettery i settery
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishtDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
