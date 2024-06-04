public class Task {
    private final int taskId;
    private String taskTitle;
    private String taskText;
    private String assignedTo;

    public Task(int taskId, String taskTitle, String taskText) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskText = taskText;
        this.assignedTo = "";  // Initialize with no assignee
    }

    public int getTaskId() {
        return taskId;
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
        return "Task ID: " + taskId + ", Title: " + taskTitle + ", Text: " + taskText + ", Assigned to: " + (assignedTo.isEmpty() ? "None" : assignedTo);
    }
}