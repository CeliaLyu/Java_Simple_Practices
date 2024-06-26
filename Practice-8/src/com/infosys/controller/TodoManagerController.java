package com.infosys.controller;

import com.infosys.service.TaskService;
import com.infosys.service.UserService;
import com.infosys.pojo.User;
import com.infosys.exceptions.InvalidUsernameException;
import com.infosys.exceptions.InvalidPasswordException;
import com.infosys.exceptions.TaskNotFoundException;
import com.infosys.exceptions.UserAlreadyExistsException;
import com.infosys.exceptions.InvalidTaskTitleException;
import com.infosys.exceptions.InvalidTaskDescriptionException;
import com.infosys.exceptions.MaxTasksReachedException;
import com.infosys.exceptions.MaxUsersReachedException;
import com.infosys.exceptions.AssignTaskToClientException;
import com.infosys.exceptions.InvalidSortOptionException;
import com.infosys.exceptions.InvalidCompletionStatusException;
import com.infosys.exceptions.UnauthorizedTaskCompletionException;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TodoManagerController {
    private static final int THREAD_POOL_SIZE = 10;
    private ExecutorService executorService;
    private TaskService taskService;
    private UserService userService;
    private User currentUser;

    public TodoManagerController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        UserService userService = new UserService();
        TodoManagerController controller = new TodoManagerController(taskService, userService);
        controller.run();
    }

    private void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            // Register a sample user for testing
            try {
                userService.registerUser("John", "Doe", User.ROLE_VISITOR, "password", "john");
                userService.registerUser("Jane", "Smith", User.ROLE_CLIENT, "password", "jane");
                System.out.println("Sample users registered: john (visitor), jane (client)");
            } catch (UserAlreadyExistsException | MaxUsersReachedException | InvalidUsernameException e) {
                System.out.println("Failed to register sample users: " + e.getMessage());
            }

            while (!exit) {
                if (currentUser == null) {
                    displayLoginMenu();
                    int choice = getIntInput(scanner);
                    exit = handleLoginChoice(choice, scanner);
                } else {
                    displayMainMenu();
                    int choice = getIntInput(scanner);
                    exit = handleMainChoice(choice, scanner);
                }
            }
        }
        executorService.shutdown();
    }

    private void displayLoginMenu() {
        System.out.println("\nTodo Manager - Login Menu:");
        System.out.println("1 - Register");
        System.out.println("2 - Login");
        System.out.println("0 - Exit");
        System.out.print("Enter your choice: ");
    }

    private boolean handleLoginChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                registerUser(scanner);
                return false;
            case 2:
                loginUser(scanner);
                return false;
            case 0:
                System.out.println("Exiting...");
                return true;
            default:
                System.out.println("Invalid choice, please try again.");
                return false;
        }
    }

    private void registerUser(Scanner scanner) {
        System.out.print("Enter first name: ");
        String firstName = scanner.next();
        System.out.print("Enter last name: ");
        String lastName = scanner.next();
        System.out.print("Enter role (1 - Client, 2 - Visitor): ");
        int role = getIntInput(scanner);
        System.out.print("Enter password: ");
        String password = scanner.next();
        System.out.print("Enter username: ");
        String username = scanner.next();
        try {
            userService.registerUser(firstName, lastName, role, password, username);
            System.out.println("User registered successfully!");
        } catch (UserAlreadyExistsException | MaxUsersReachedException e) {
            System.out.println("Registration failed: " + e.getMessage());
        } catch (InvalidUsernameException e) {
            throw new RuntimeException(e);
        }
    }

    private void loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        try {
            currentUser = userService.loginUser(username, password);
            System.out.println("Login successful!");
        } catch (InvalidUsernameException | InvalidPasswordException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private void displayMainMenu() {
        System.out.println("\nTodo Manager - Main Menu:");
        if (currentUser.getRole() == User.ROLE_CLIENT) {
            System.out.println("1 - Add Task");
            System.out.println("2 - Update Task");
            System.out.println("3 - Delete Task");
            System.out.println("4 - List All Tasks");
            System.out.println("5 - Assign Task");
            System.out.println("6 - Search Task");
            System.out.println("8 - Sort Tasks");
        } else if (currentUser.getRole() == User.ROLE_VISITOR) {
            System.out.println("4 - List Tasks Assigned to Me");
            System.out.println("9 - Mark Task as Completed");
            System.out.println("10 - View Tasks by Completion Status");
        }
        System.out.println("7 - Logout");
        System.out.println("0 - Exit");
        System.out.print("Enter your choice: ");
    }

    private boolean handleMainChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                executorService.submit(() -> addTask(scanner));
                return false;
            case 2:
                executorService.submit(() -> updateTask(scanner));
                return false;
            case 3:
                executorService.submit(() -> deleteTask(scanner));
                return false;
            case 4:
                executorService.submit(() -> listTasks(scanner));
                return false;
            case 5:
                executorService.submit(() -> assignTask(scanner));
                return false;
            case 6:
                executorService.submit(() -> searchTask(scanner));
                return false;
            case 7:
                currentUser = null;
                System.out.println("Logged out successfully!");
                return false;
            case 8:
                executorService.submit(() -> {
                    try {
                        sortTasks(scanner);
                    } catch (InvalidSortOptionException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                });
                return false;
            case 9:
                executorService.submit(() -> {
                    try {
                        markTaskAsCompleted(scanner);
                    } catch (UnauthorizedTaskCompletionException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                });
                return false;
            case 10:
                executorService.submit(() -> {
                    try {
                        viewTasksByCompletionStatus(scanner);
                    } catch (InvalidCompletionStatusException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                });
                return false;
            case 0:
                System.out.println("Exiting...");
                return true;
            default:
                System.out.println("Invalid choice, please try again.");
                return false;
        }
    }

    private void addTask(Scanner scanner) {
        try {
            taskService.addTask(scanner);
        } catch (MaxTasksReachedException | InvalidTaskTitleException | InvalidTaskDescriptionException e) {
            System.out.println("Failed to add task: " + e.getMessage());
        }
    }

    private void updateTask(Scanner scanner) {
        try {
            taskService.updateTask(scanner);
        } catch (TaskNotFoundException | InvalidTaskTitleException | InvalidTaskDescriptionException e) {
            System.out.println("Failed to update task: " + e.getMessage());
        }
    }

    private void deleteTask(Scanner scanner) {
        try {
            taskService.deleteTask(scanner);
        } catch (TaskNotFoundException e) {
            System.out.println("Failed to delete task: " + e.getMessage());
        }
    }

    private void listTasks(Scanner scanner) {
        if (currentUser.getRole() == User.ROLE_CLIENT) {
            taskService.listAllTasks();
        } else {
            System.out.print("Enter 1 for ascending, 0 for descending order: ");
            boolean ascending = getIntInput(scanner) == 1;
            taskService.listTasksAssignedToVisitor(currentUser.getUserName(), ascending);
        }
    }

    private void assignTask(Scanner scanner) {
        try {
            taskService.assignTask(scanner, currentUser);
        } catch (TaskNotFoundException | AssignTaskToClientException | InvalidUsernameException e) {
            System.out.println("Failed to assign task: " + e.getMessage());
        }
    }

    private void searchTask(Scanner scanner) {
        try {
            taskService.searchTask(scanner);
        } catch (TaskNotFoundException e) {
            System.out.println("Failed to find task: " + e.getMessage());
        }
    }

    private void sortTasks(Scanner scanner) throws InvalidSortOptionException {
        System.out.print("Enter 1 for ascending, 0 for descending: ");
        int sortOption = getIntInput(scanner);
        if (sortOption != 1 && sortOption != 0) {
            throw new InvalidSortOptionException("Invalid sort option. Please enter 1 for ascending or 0 for descending.");
        }
        boolean ascending = sortOption == 1;
        taskService.listAllTasksSorted(ascending);
    }

    private void markTaskAsCompleted(Scanner scanner) throws UnauthorizedTaskCompletionException {
        System.out.print("Enter task ID to mark as completed: ");
        int taskId = getIntInput(scanner);
        try {
            taskService.markTaskAsCompleted(taskId, currentUser);
        } catch (TaskNotFoundException e) {
            System.out.println("Failed to mark task as completed: " + e.getMessage());
        }
    }

    private void viewTasksByCompletionStatus(Scanner scanner) throws InvalidCompletionStatusException {
        System.out.print("Enter 1 for completed tasks, 0 for incomplete tasks: ");
        int statusOption = getIntInput(scanner);
        if (statusOption != 1 && statusOption != 0) {
            throw new InvalidCompletionStatusException("Invalid status option. Please enter 1 for completed tasks or 0 for incomplete tasks.");
        }
        boolean isCompleted = statusOption == 1;
        taskService.listTasksByCompletionStatus(isCompleted, currentUser.getRole(), currentUser.getUserName());
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
