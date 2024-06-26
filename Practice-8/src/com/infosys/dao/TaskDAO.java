package com.infosys.dao;

import com.infosys.pojo.Task;
import com.infosys.exceptions.TaskNotFoundException;
import com.infosys.exceptions.MaxTasksReachedException;
import com.infosys.exceptions.InvalidTaskTitleException;
import com.infosys.exceptions.InvalidTaskDescriptionException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDAO {
    private List<Task> tasks = new ArrayList<>();
    private static final int MAX_TASKS = 5;
    private static int taskIdCounter = 1;  // To auto-increment task IDs

    public void addTask(Task task) throws MaxTasksReachedException, InvalidTaskTitleException, InvalidTaskDescriptionException {
        if (tasks.size() >= MAX_TASKS) {
            throw new MaxTasksReachedException("Cannot add more tasks. Maximum limit of " + MAX_TASKS + " tasks reached.");
        }
        if (task.getTaskTitle() == null || task.getTaskTitle().isEmpty()) {
            throw new InvalidTaskTitleException("Task title cannot be empty.");
        }
        if (task.getTaskText() == null || task.getTaskText().isEmpty()) {
            throw new InvalidTaskDescriptionException("Task description cannot be empty.");
        }
        task.setTaskId(taskIdCounter++);
        tasks.add(task);
        System.out.println("Task added successfully. Total tasks: " + tasks.size());
    }

    public void updateTask(int taskId, String title, String text) throws TaskNotFoundException, InvalidTaskTitleException, InvalidTaskDescriptionException {
        Task task = findTaskById(taskId);
        if (task != null) {
            if (title == null || title.isEmpty()) {
                throw new InvalidTaskTitleException("Task title cannot be empty.");
            }
            if (text == null || text.isEmpty()) {
                throw new InvalidTaskDescriptionException("Task description cannot be empty.");
            }
            task.setTaskTitle(title);
            task.setTaskText(text);
            System.out.println("Task updated successfully.");
        } else {
            throw new TaskNotFoundException("Task not found.");
        }
    }

    public void deleteTask(int taskId) throws TaskNotFoundException {
        boolean removed = tasks.removeIf(task -> task.getTaskId() == taskId);
        if (!removed) {
            throw new TaskNotFoundException("Task not found.");
        }
        System.out.println("Task deleted successfully.");
    }

    public Task findTaskById(int taskId) throws TaskNotFoundException {
        return tasks.stream()
                .filter(t -> t.getTaskId() == taskId)
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public void assignTask(int taskId, String assignee) throws TaskNotFoundException {
        Task task = findTaskById(taskId);
        task.setAssignedTo(assignee);
        System.out.println("Task assigned successfully to " + assignee);
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

    public void markTaskAsCompleted(int taskId) throws TaskNotFoundException {
        Task task = findTaskById(taskId);
        task.completeTask();
        System.out.println("Task marked as completed successfully.");
    }
}
