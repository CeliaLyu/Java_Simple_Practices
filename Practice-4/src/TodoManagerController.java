import java.util.Scanner;

public class TodoManagerController {
    private TaskService taskService;
    private UserService userService;
    private User currentUser;

    public TodoManagerController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
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
        userService.registerUser(firstName, lastName, role, password, username);
        System.out.println("User registered successfully!");
    }

    private void loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        currentUser = userService.loginUser(username, password);
        if (currentUser != null) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void displayMainMenu() {
        System.out.println("\nTodo Manager - Main Menu:");

        if (currentUser.getRole() == 1) { // Client
            System.out.println("1 - Add Task");
            System.out.println("2 - Update Task");
            System.out.println("3 - Delete Task");
            System.out.println("4 - List All Tasks");
            System.out.println("5 - Assign Task");
            System.out.println("6 - Search Task");
        } else if (currentUser.getRole() == 2) { // Visitor
            System.out.println("4 - List Tasks Assigned to Me");
        }

        System.out.println("7 - Logout");
        System.out.println("0 - Exit");
        System.out.print("Enter your choice: ");
    }

    private boolean handleMainChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                if (currentUser.getRole() == 1) {
                    taskService.addTask(scanner);
                } else {
                    System.out.println("Access denied. Only clients can add tasks.");
                }
                return false;
            case 2:
                if (currentUser.getRole() == 1) {
                    taskService.updateTask(scanner);
                } else {
                    System.out.println("Access denied. Only clients can update tasks.");
                }
                return false;
            case 3:
                if (currentUser.getRole() == 1) {
                    taskService.deleteTask(scanner);
                } else {
                    System.out.println("Access denied. Only clients can delete tasks.");
                }
                return false;
            case 4:
                if (currentUser.getRole() == 1) {
                    taskService.listTasks();
                } else if (currentUser.getRole() == 2) {
                    taskService.listTasksAssignedToVisitor(currentUser.getUserName());
                }
                return false;
            case 5:
                if (currentUser.getRole() == 1) {
                    taskService.assignTask(scanner);
                } else {
                    System.out.println("Access denied. Only clients can assign tasks.");
                }
                return false;
            case 6:
                if (currentUser.getRole() == 1) {
                    taskService.searchTask(scanner);
                } else {
                    System.out.println("Access denied. Only clients can search tasks.");
                }
                return false;
            case 7:
                currentUser = null;
                System.out.println("Logged out successfully!");
                return false;
            case 0:
                System.out.println("Exiting...");
                return true;
            default:
                System.out.println("Invalid choice, please try again.");
                return false;
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