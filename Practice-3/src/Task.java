public class Task {
    private int taskId;
    private String taskTitle;
    private String taskText;
    private String assignedTo;  // Optional: Assuming you might use it later.

    // Constructor that correctly takes an int and two strings
    public Task(int taskId, String taskTitle, String taskText) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskText = taskText;
        this.assignedTo = ""; // Initialize with no assignee
    }

    // Getters and setters for all fields
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

    @Override
    public String toString() {
        return "Task ID: " + taskId + ", Title: " + taskTitle + ", Text: " + taskText + ", Assigned to: " + assignedTo;
    }
}
