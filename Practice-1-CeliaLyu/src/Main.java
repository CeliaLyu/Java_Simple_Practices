//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    private static final int MAX_TASKS = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Task 1: Print user's name
        String name = getUserName(scanner);
        System.out.println("Name: " + name);

        // Task 2: List at least 5 tasks for the day
        String[] tasks = getUserTasks(scanner);

        // Task 3: See all tasks in ascending or descending order
        displayTasksInOrder(scanner, tasks);

        // Task 4: See the repeated tasks
        displayRepeatedTasks(tasks);
    }

    // Task 1: Get user's name
    private static String getUserName(Scanner scanner) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.print("Enter your name: ");
        return scanner.nextLine();
    }

    // Task 2: Get user's tasks
    private static String[] getUserTasks(Scanner scanner) {
        String[] tasks = new String[MAX_TASKS];
        System.out.println("\nEnter " + MAX_TASKS + " tasks for the day:");
        for (int i = 0; i < MAX_TASKS; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.print("Task " + (i + 1) + ": ");
            tasks[i] = scanner.nextLine();
        }
        return tasks;
    }

    // Task 3: Display tasks in chosen order
    private static void displayTasksInOrder(Scanner scanner, String[] tasks) {
        System.out.println("\nChoose the order of tasks:");
        System.out.println("0 - Ascending order");
        System.out.println("1 - Descending order");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        if (choice == 0) {
            System.out.println("\nTasks in ascending order:");
            Arrays.sort(tasks);
        } else if (choice == 1) {
            System.out.println("\nTasks in descending order:");
            Arrays.sort(tasks, Collections.reverseOrder());
        } else {
            System.out.println("\nInvalid choice. Displaying tasks in default order.");
        }

        for (String task : tasks) {
            System.out.println(task);
        }
    }

    // Task 4: Display repeated tasks
    private static void displayRepeatedTasks(String[] tasks) {
        System.out.println("\nRepeated tasks:");
        for (int i = 0; i < tasks.length; i++) {
            for (int j = i + 1; j < tasks.length; j++) {
                if (tasks[i].equals(tasks[j])) {
                    System.out.println(tasks[i]);
                    break;
                }
            }
        }
    }
}