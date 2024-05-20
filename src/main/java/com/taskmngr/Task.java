package com.taskmngr;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Task {
    private String task;
    private String description;
    private String worker;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String status;
    private int countDays;

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

    public LocalDate getFinishDate() {
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

    public int getCountDays() {
        return countDays;
    }

    public void setCountDays(int countDays) {
        this.countDays = countDays;
    }

    public long getDaysBetween() {
        if (startDate != null && finishDate != null) {
            return ChronoUnit.DAYS.between(startDate, finishDate);
        }
        return 0;
    }
}
