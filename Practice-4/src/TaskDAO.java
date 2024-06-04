import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDAO {
    private List<Task> tasks = new ArrayList<>(5);
    private static int taskIdCounter = 1;

    public void addTask(String title, String text) {
        Task newTask = new Task(taskIdCounter++, title, text);
        tasks.add(newTask);
        System.out.println("Task added. Array size: " + tasks.size());
    }

    public boolean updateTask(int taskId, String title, String text) {
        Task task = findTaskById(taskId);
        if (task != null) {
            task.setTaskTitle(title);
            task.setTaskText(text);
            System.out.println("Task updated. Array size: " + tasks.size());
            return true;
        }
        System.out.println("Task not found. Array size: " + tasks.size());
        return false;
    }

    public boolean deleteTask(int taskId) {
        boolean removed = tasks.removeIf(task -> task.getTaskId() == taskId);
        if (removed) {
            System.out.println("Task deleted. Array size: " + tasks.size());
        } else {
            System.out.println("Task not found. Array size: " + tasks.size());
        }
        return removed;
    }

    public Task findTaskById(int taskId) {
        Task task = tasks.stream()
                .filter(t -> t.getTaskId() == taskId)
                .findFirst()
                .orElse(null);
        System.out.println("Task found. Array size: " + tasks.size());
        return task;
    }

    public List<Task> getAllTasks() {
        System.out.println("All tasks retrieved. Array size: " + tasks.size());
        return new ArrayList<>(tasks);
    }

    public boolean assignTask(int taskId, String assignee) {
        Task task = findTaskById(taskId);
        if (task != null) {
            task.setAssignedTo(assignee);
            System.out.println("Task assigned. Array size: " + tasks.size());
            return true;
        }
        System.out.println("Task not found. Array size: " + tasks.size());
        return false;
    }

    public List<Task> getTasksAssignedToVisitor(String visitorUsername) {
        return tasks.stream()
                .filter(task -> task.getAssignedTo().equals(visitorUsername))
                .collect(Collectors.toList());
    }
}