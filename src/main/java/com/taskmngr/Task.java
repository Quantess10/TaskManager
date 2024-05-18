package com.taskmngr;

public class Task {
    private String task;
    private String description;
    private String worker;

    public Task(String task, String description, String worker) {
        this.task = task;
        this.description = description;
        this.worker = worker;
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
}
