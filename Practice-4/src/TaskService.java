import java.util.Scanner;

public class TaskService {
    private TaskDAO taskDao;

    public TaskService() {
        this.taskDao = new TaskDAO();
    }

    public void addTask(Scanner scanner) {
        System.out.print("Enter task title: ");
        scanner.nextLine();  // Consume newline left-over
        String title = scanner.nextLine();
        System.out.print("Enter task text: ");
        String text = scanner.nextLine();
        taskDao.addTask(title, text);
        System.out.println("Task added successfully!");
    }

    public void updateTask(Scanner scanner) {
        System.out.print("Enter task ID to update: ");
        int taskId = getIntInput(scanner);
        scanner.nextLine();  // Consume newline left-over
        System.out.print("Enter new task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new task text: ");
        String text = scanner.nextLine();
        if (taskDao.updateTask(taskId, title, text)) {
            System.out.println("Task updated successfully!");
        } else {
            System.out.println("Task not found.");
        }
    }

    public void deleteTask(Scanner scanner) {
        System.out.print("Enter task ID to delete: ");
        int taskId = getIntInput(scanner);
        if (taskDao.deleteTask(taskId)) {
            System.out.println("Task deleted successfully!");
        } else {
            System.out.println("Task not found.");
        }
    }

    public void listTasks() {
        System.out.println("List of Tasks:");
        taskDao.getAllTasks().forEach(System.out::println);
    }

    public void assignTask(Scanner scanner) {
        System.out.print("Enter task ID to assign: ");
        int taskId = getIntInput(scanner);
        scanner.nextLine();  // Consume newline left-over
        System.out.print("Enter assignee username: ");
        String assignee = scanner.nextLine();
        if (taskDao.assignTask(taskId, assignee)) {
            System.out.println("Task assigned successfully!");
        } else {
            System.out.println("Task or assignee not found.");
        }
    }

    public void searchTask(Scanner scanner) {
        System.out.print("Enter task ID to search: ");
        int taskId = getIntInput(scanner);
        Task task = taskDao.findTaskById(taskId);
        if (task != null) {
            System.out.println("Task found:");
            System.out.println(task);
        } else {
            System.out.println("Task not found.");
        }
    }

    public void listTasksAssignedToVisitor(String visitorUsername) {
        System.out.println("Tasks assigned to visitor " + visitorUsername + ":");
        taskDao.getTasksAssignedToVisitor(visitorUsername).forEach(System.out::println);
    }

    private int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input, please enter a valid number.");
                scanner.next();  // Consume the non-integer input
            }
        }
    }
}