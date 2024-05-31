import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    // Constants for task array size and a counter for assigning unique task IDs.
    private static final int MAX_TASKS = 5;
    private static Task[] tasks = new Task[MAX_TASKS];
    private static int currentTaskId = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Main application loop.
        while (!exit) {
            // Display the main menu to the user.
            System.out.println("\nTodo Manager - Choose an option:");
            System.out.println("1 - Add Task");
            System.out.println("2 - Update Task");
            System.out.println("3 - Delete Task");
            System.out.println("4 - List All Tasks");
            System.out.println("0 - Exit");
            System.out.print("Enter your choice: ");

            // Safe reading of an integer from the user input.
            int choice = getIntInput(scanner);
            scanner.nextLine(); // Consume any leftover newline character.

            // Switch statement to handle user choices.
            switch (choice) {
                case 1:
                    addTask(scanner);
                    break;
                case 2:
                    updateTask(scanner);
                    break;
                case 3:
                    deleteTask(scanner);
                    break;
                case 4:
                    listTasks();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }

    // Method to safely get an integer input from the user, handling any input mismatches.
    private static int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a valid number.");
                scanner.next(); // Consume the invalid input and prompt again.
            }
        }
    }

    // Method to add a new task to the array of tasks.
    private static void addTask(Scanner scanner) {
        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine();
        if (currentTaskId <= MAX_TASKS) {
            tasks[currentTaskId - 1] = new Task(currentTaskId, taskName);
            System.out.println("Task added: " + tasks[currentTaskId - 1]);
            currentTaskId++;
        } else {
            System.out.println("Maximum task limit reached.");
        }
    }

    // Method to update an existing task based on its ID.
    private static void updateTask(Scanner scanner) {
        System.out.print("Enter task ID to update: ");
        int taskId = getIntInput(scanner); // Use safe integer input method
        scanner.nextLine(); // Consume newline

        if (taskId > 0 && taskId <= MAX_TASKS && tasks[taskId - 1] != null) {
            System.out.print("Enter new task name: ");
            String taskName = scanner.nextLine();
            tasks[taskId - 1].setTaskName(taskName);
            System.out.println("Task updated: " + tasks[taskId - 1]);
        } else {
            System.out.println("Task not found.");
        }
    }

    // Method to delete a task from the array using its ID.
    private static void deleteTask(Scanner scanner) {
        System.out.print("Enter task ID to delete: ");
        int taskId = getIntInput(scanner); // Ensure the input is an integer
        if (taskId > 0 && taskId <= MAX_TASKS && tasks[taskId - 1] != null) {
            tasks[taskId - 1] = null;
            System.out.println("Task deleted.");
        } else {
            System.out.println("Task not found.");
        }
    }

    // Method to list all tasks currently in the array.
    private static void listTasks() {
        System.out.println("List of Tasks:");
        for (Task task : tasks) {
            if (task != null) {
                System.out.println(task);
            } else {
                System.out.println("Empty task slot.");
            }
        }
    }
}
