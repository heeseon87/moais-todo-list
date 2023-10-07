package moais.todo.task.dto;

import moais.todo.task.entity.Task.Progress;

public record UpdateTaskReqDto(String title, String content, Progress progress) {
}
