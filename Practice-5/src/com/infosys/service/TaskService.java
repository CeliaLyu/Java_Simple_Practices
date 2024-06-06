package com.infosys.service;

import com.infosys.dao.TaskDAO;
import com.infosys.pojo.Task;
import com.infosys.pojo.User;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Comparator;

public class TaskService {
    private TaskDAO taskDao;

    public TaskService() {
        this.taskDao = new TaskDAO();
    }

    public void addTask(Scanner scanner) {
        System.out.print("Enter task title: ");
        scanner.nextLine();  // Consume newline left-over
        String title = scanner.nextLine();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        Task newTask = new Task(title, description);
        if (taskDao.addTask(newTask)) {
            System.out.println("Task added successfully!");
        } else {
            System.out.println("Failed to add task. Maximum task limit may have been reached.");
        }
    }

    public void updateTask(Scanner scanner) {
        System.out.print("Enter task ID to update: ");
        int taskId = getIntInput(scanner);
        scanner.nextLine();  // Consume newline left-over
        System.out.print("Enter new task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new task description: ");
        String description = scanner.nextLine();
        if (taskDao.updateTask(taskId, title, description)) {
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

    public void listAllTasks() {
        List<Task> tasks = taskDao.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    public void listTasksAssignedToVisitor(String visitorUsername, boolean ascending) {
        List<Task> tasks = taskDao.getTasksAssignedToVisitor(visitorUsername);
        if (tasks.isEmpty()) {
            System.out.println("No tasks assigned to this visitor.");
        } else {
            Comparator<Task> byTaskId = ascending ? Comparator.comparing(Task::getTaskId) : Comparator.comparing(Task::getTaskId).reversed();
            tasks = tasks.stream()
                    .sorted(byTaskId)
                    .collect(Collectors.toList());
            System.out.println("Tasks assigned to visitor " + visitorUsername + ":");
            tasks.forEach(task -> System.out.println("Task ID: " + task.getTaskId() + ", Title: " + task.getTaskTitle() + ", Text: " + task.getTaskText()));
        }
    }

    public void listAllTasksSorted(boolean ascending) {
        List<Task> sortedTasks = taskDao.getAllTasksSorted(ascending);
        if (sortedTasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            sortedTasks.forEach(System.out::println);
        }
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

    public void markTaskAsCompleted(int taskId) {
        if (taskDao.markTaskAsCompleted(taskId)) {
            System.out.println("Task marked as completed successfully!");
        } else {
            System.out.println("Failed to mark task as completed or task not found.");
        }
    }

    public void listTasksByCompletionStatus(boolean isCompleted, int currentUserRole, String visitorUsername) {
        List<Task> tasks = taskDao.getTasksByCompletionStatus(isCompleted);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found for the specified completion status.");
        } else {
            if (!isCompleted && currentUserRole == User.ROLE_VISITOR) {
                tasks = tasks.stream()
                        .filter(task -> task.getAssignedTo().equals(visitorUsername) && !task.isCompleted())
                        .collect(Collectors.toList());
                if (tasks.isEmpty()) {
                    System.out.println("No incomplete tasks assigned to you.");
                    return;
                }
            }
            System.out.println("Displaying " + (isCompleted ? "completed" : "incomplete") + " tasks:");
            tasks.forEach(task -> System.out.println("Task ID: " + task.getTaskId() + ", Title: " + task.getTaskTitle() + ", Text: " + task.getTaskText() + ", Assigned to: " + task.getAssignedTo()));
        }
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
