package moais.todo.task.dto;

import moais.todo.task.entity.Task.Progress;

public record UpdateTaskResDto(Long id, String title, String content, Progress progress) {
}
