package moais.todo.task.fixtures;

import moais.todo.member.entity.Member;
import moais.todo.task.entity.Task;

public class TaskFixtures {

	public static Task createBy(Member member, String title, String content) {
		Task task = new Task();
		task.setMember(member);
		task.setTitle(title);
		task.setContent(content);
		task.setProgress(Task.Progress.TODO);
		return task;
	}
}
