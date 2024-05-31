import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    private static final int MAX_TASKS = 5; // Maximum number of tasks.
    private static Task[] tasks = new Task[MAX_TASKS]; // Array to store Task objects.
    private static int currentTaskId = 1; // Static counter to manage unique task IDs.

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nTodo Manager - Choose an option:");
            System.out.println("1 - Add Task");
            System.out.println("2 - Update Task");
            System.out.println("3 - Delete Task");
            System.out.println("4 - List All Tasks");
            System.out.println("5 - Assign Task");
            System.out.println("0 - Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(scanner);
            scanner.nextLine(); // Consume newline

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
                case 5:
                    assignTask(scanner);
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

    private static void addTask(Scanner scanner) {
        if (currentTaskId > MAX_TASKS) {
            System.out.println("Maximum task limit reached.");
            return;
        }

        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter task text: ");
        String text = scanner.nextLine();

        if (currentTaskId <= MAX_TASKS) {
            tasks[currentTaskId - 1] = new Task(currentTaskId, title, text);
            System.out.println("Task added: " + tasks[currentTaskId - 1]);
            currentTaskId++; // Increment only after adding to prevent ArrayIndexOutOfBoundsException
        }
    }

    private static void updateTask(Scanner scanner) {
        System.out.print("Enter task ID to update: ");
        int taskId = getIntInput(scanner);
        scanner.nextLine();
        int index = taskId - 1;

        if (index >= 0 && index < MAX_TASKS && tasks[index] != null) {
            System.out.print("Enter new task title: ");
            String title = scanner.nextLine();
            System.out.print("Enter new task text: ");
            String text = scanner.nextLine();
            tasks[index].setTaskTitle(title);
            tasks[index].setTaskText(text);
            System.out.println("Task updated: " + tasks[index]);
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void deleteTask(Scanner scanner) {
        System.out.print("Enter task ID to delete: ");
        int taskId = getIntInput(scanner);
        int index = taskId - 1;

        if (index >= 0 && index < MAX_TASKS && tasks[index] != null) {
            tasks[index] = null;
            System.out.println("Task deleted.");
        } else {
            System.out.println("Task not found.");
        }
    }

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

    private static void assignTask(Scanner scanner) {
        System.out.print("Enter task ID to assign: ");
        int taskId = getIntInput(scanner);
        scanner.nextLine();
        int index = taskId - 1;

        if (index >= 0 && index < MAX_TASKS && tasks[index] != null) {
            System.out.print("Enter assignee name: ");
            String assignee = scanner.nextLine();
            tasks[index].setAssignedTo(assignee);
            System.out.println("Task assigned: " + tasks[index]);
        } else {
            System.out.println("Task not found.");
        }
    }
}
