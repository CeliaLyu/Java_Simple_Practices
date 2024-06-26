package com.infosys.pojo;

public class Task {
    private int taskId;
    private String taskTitle;
    private String taskText;
    private String assignedTo;
    private boolean isCompleted;

    // Constructor without taskId, since it will be set after creation in the DAO
    public Task(String taskTitle, String taskText) {
        this.taskTitle = taskTitle;
        this.taskText = taskText;
        this.assignedTo = "";  // Initialize with no assignee
        this.isCompleted = false;  // Initialize as not completed
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void completeTask() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return "Task ID: " + taskId + ", Title: " + taskTitle + ", Text: " + taskText + ", Assigned to: " + (assignedTo.isEmpty() ? "None" : assignedTo);
    }
}
