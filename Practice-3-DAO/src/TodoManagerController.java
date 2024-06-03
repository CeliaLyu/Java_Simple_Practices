import java.util.Scanner;

public class TodoManagerController {
    private TaskService taskService;

    public TodoManagerController(TaskService taskService) {
        this.taskService = taskService;
    }

    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        TodoManagerController controller = new TodoManagerController(taskService);
        controller.run();
    }

    private void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                displayMenu();
                int choice = getIntInput(scanner);
                exit = handleChoice(choice, scanner);
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nTodo Manager - Choose an option:");
        System.out.println("1 - Add Task");
        System.out.println("2 - Update Task");
        System.out.println("3 - Delete Task");
        System.out.println("4 - List All Tasks");
        System.out.println("5 - Assign Task");
        System.out.println("0 - Exit");
        System.out.print("Enter your choice: ");
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

    private boolean handleChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                taskService.addTask(scanner);
                return false;
            case 2:
                taskService.updateTask(scanner);
                return false;
            case 3:
                taskService.deleteTask(scanner);
                return false;
            case 4:
                taskService.listTasks();
                return false;
            case 5:
                taskService.assignTask(scanner);
                return false;
            case 0:
                System.out.println("Exiting...");
                return true;
            default:
                System.out.println("Invalid choice, please try again.");
                return false;
        }
    }
}
