package com.infosys.dao;

import com.infosys.pojo.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDAO {
    private List<Task> tasks = new ArrayList<>();
    private static final int MAX_TASKS = 5;
    private static int taskIdCounter = 1;  // To auto-increment task IDs

    public boolean addTask(Task task) {
        if (tasks.size() >= MAX_TASKS) {
            System.out.println("Cannot add more tasks. Maximum limit of " + MAX_TASKS + " tasks reached.");
            return false;
        }
        task.setTaskId(tasks.size() + 1);  // This assumes Task IDs are sequentially assigned
        tasks.add(task);
        System.out.println("Task added successfully. Total tasks: " + tasks.size());
        return true;
    }

    public boolean updateTask(int taskId, String title, String text) {
        Task task = findTaskById(taskId);
        if (task != null) {
            task.setTaskTitle(title);
            task.setTaskText(text);
            System.out.println("Task updated successfully.");
            return true;
        } else {
            System.out.println("Task not found.");
            return false;
        }
    }

    public boolean deleteTask(int taskId) {
        boolean removed = tasks.removeIf(task -> task.getTaskId() == taskId);
        if (removed) {
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Task not found.");
        }
        return removed;
    }

    public Task findTaskById(int taskId) {
        return tasks.stream()
                .filter(t -> t.getTaskId() == taskId)
                .findFirst()
                .orElse(null);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public boolean assignTask(int taskId, String assignee) {
        Task task = findTaskById(taskId);
        if (task != null) {
            task.setAssignedTo(assignee);
            System.out.println("Task assigned successfully to " + assignee);
            return true;
        } else {
            System.out.println("Task or assignee not found.");
            return false;
        }
    }

    public List<Task> getTasksAssignedToVisitor(String visitorUsername) {
        return tasks.stream()
                .filter(task -> task.getAssignedTo().equals(visitorUsername))
                .collect(Collectors.toList());
    }

    public List<Task> getAllTasksSorted(boolean ascending) {
        if (ascending) {
            return tasks.stream()
                    .sorted((t1, t2) -> t1.getTaskId() - t2.getTaskId())
                    .collect(Collectors.toList());
        } else {
            return tasks.stream()
                    .sorted((t1, t2) -> t2.getTaskId() - t1.getTaskId())
                    .collect(Collectors.toList());
        }
    }

    public List<Task> getTasksByCompletionStatus(boolean isCompleted) {
        return tasks.stream()
                .filter(task -> task.isCompleted() == isCompleted)
                .collect(Collectors.toList());
    }

    public boolean markTaskAsCompleted(int taskId) {
        Task task = findTaskById(taskId);
        if (task != null) {
            task.completeTask();
            System.out.println("Task marked as completed successfully.");
            return true;
        } else {
            System.out.println("Task not found.");
            return false;
        }
    }
}
