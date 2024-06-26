package com.infosys.service;

import com.infosys.dao.TaskDAO;
import com.infosys.dao.UserDAO;
import com.infosys.pojo.Task;
import com.infosys.pojo.User;
import com.infosys.exceptions.TaskNotFoundException;
import com.infosys.exceptions.InvalidTaskTitleException;
import com.infosys.exceptions.InvalidTaskDescriptionException;
import com.infosys.exceptions.MaxTasksReachedException;
import com.infosys.exceptions.AssignTaskToClientException;
import com.infosys.exceptions.UnauthorizedTaskCompletionException;
import com.infosys.exceptions.InvalidUsernameException;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Comparator;

public class TaskService {
    private TaskDAO taskDao;
    private UserDAO userDao;

    public TaskService() {
        this.taskDao = new TaskDAO();
        this.userDao = new UserDAO();
        // Register sample users directly in the UserDAO for testing
        try {
            userDao.addUser(new User("John", "Doe", User.ROLE_VISITOR, "password", "john"));
            userDao.addUser(new User("Jane", "Smith", User.ROLE_CLIENT, "password", "jane"));
        } catch (InvalidUsernameException e) {
            System.out.println("Failed to register sample users in TaskService: " + e.getMessage());
        }
    }

    public synchronized void addTask(Scanner scanner) throws MaxTasksReachedException, InvalidTaskTitleException, InvalidTaskDescriptionException {
        System.out.print("Enter task title: ");
        scanner.nextLine();  // Consume newline left-over
        String title = scanner.nextLine();
        validateTaskTitle(title);

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        validateTaskDescription(description);

        Task newTask = new Task(title, description);
        taskDao.addTask(newTask);
        System.out.println("Task added successfully!");
    }

    public synchronized void updateTask(Scanner scanner) throws TaskNotFoundException, InvalidTaskTitleException, InvalidTaskDescriptionException {
        System.out.print("Enter task ID to update: ");
        int taskId = getIntInput(scanner);
        scanner.nextLine();  // Consume newline left-over

        System.out.print("Enter new task title: ");
        String title = scanner.nextLine();
        validateTaskTitle(title);

        System.out.print("Enter new task description: ");
        String description = scanner.nextLine();
        validateTaskDescription(description);

        taskDao.updateTask(taskId, title, description);
        System.out.println("Task updated successfully!");
    }

    public synchronized void deleteTask(Scanner scanner) throws TaskNotFoundException {
        System.out.print("Enter task ID to delete: ");
        int taskId = getIntInput(scanner);
        taskDao.deleteTask(taskId);
        System.out.println("Task deleted successfully!");
    }

    public synchronized void listAllTasks() {
        List<Task> tasks = taskDao.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    public synchronized void listTasksAssignedToVisitor(String visitorUsername, boolean ascending) {
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

    public synchronized void listAllTasksSorted(boolean ascending) {
        List<Task> sortedTasks = taskDao.getAllTasksSorted(ascending);
        if (sortedTasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            sortedTasks.forEach(System.out::println);
        }
    }

    public synchronized void assignTask(Scanner scanner, User currentUser) throws TaskNotFoundException, AssignTaskToClientException, InvalidUsernameException {
        System.out.print("Enter task ID to assign: ");
        int taskId = getIntInput(scanner);
        scanner.nextLine();  // Consume newline left-over
        System.out.print("Enter assignee username: ");
        String assignee = scanner.nextLine();

        // Check if the assignee exists
        System.out.println("Looking for user: " + assignee);  // Debug print
        User assigneeUser = userDao.findUserByUsername(assignee);  // Ensure this correctly finds the user
        if (assigneeUser == null) {
            throw new InvalidUsernameException("User not found.");
        }
        System.out.println("Found user: " + assigneeUser.getUserName());  // Debug print
        if (assigneeUser.getRole() == User.ROLE_CLIENT) {
            throw new AssignTaskToClientException("Tasks can only be assigned to visitors, not clients.");
        }

        taskDao.assignTask(taskId, assignee);
        System.out.println("Task assigned successfully!");
    }

    public synchronized void searchTask(Scanner scanner) throws TaskNotFoundException {
        System.out.print("Enter task ID to search: ");
        int taskId = getIntInput(scanner);
        Task task = taskDao.findTaskById(taskId);
        System.out.println("Task found:");
        System.out.println(task);
    }

    public synchronized void markTaskAsCompleted(int taskId, User currentUser) throws TaskNotFoundException, UnauthorizedTaskCompletionException {
        Task task = taskDao.findTaskById(taskId);
        if (!task.getAssignedTo().equals(currentUser.getUserName())) {
            throw new UnauthorizedTaskCompletionException("You can only mark tasks assigned to you as completed.");
        }
        taskDao.markTaskAsCompleted(taskId);
        System.out.println("Task marked as completed successfully!");
    }

    public synchronized void listTasksByCompletionStatus(boolean isCompleted, int currentUserRole, String visitorUsername) {
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

    private void validateTaskTitle(String title) throws InvalidTaskTitleException {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidTaskTitleException("Task title cannot be empty.");
        }
        if (title.length() < 3) {
            throw new InvalidTaskTitleException("Task title must be at least 3 characters long.");
        }
        if (title.length() > 50) {
            throw new InvalidTaskTitleException("Task title must be less than 50 characters long.");
        }
    }

    private void validateTaskDescription(String description) throws InvalidTaskDescriptionException {
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidTaskDescriptionException("Task description cannot be empty.");
        }
        if (description.length() < 10) {
            throw new InvalidTaskDescriptionException("Task description must be at least 10 characters long.");
        }
        if (description.length() > 200) {
            throw new InvalidTaskDescriptionException("Task description must be less than 200 characters long.");
        }
    }
}
