package moais.todo.task.service;

import static moais.todo.task.entity.Task.Progress.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import moais.todo.member.entity.Member;
import moais.todo.member.fixtures.MemberFixtures;
import moais.todo.member.repository.MemberRepository;
import moais.todo.task.dto.CreateTaskReqDto;
import moais.todo.task.dto.CreateTaskResDto;
import moais.todo.task.dto.UpdateTaskReqDto;
import moais.todo.task.dto.UpdateTaskResDto;
import moais.todo.task.entity.Task;
import moais.todo.task.fixtures.TaskFixtures;
import moais.todo.task.repository.TaskRepository;

@DataJpaTest
@Import({TaskService.class})
class TaskServiceTest {

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("[정상] 사용자의 할일 목록 조회")
	@Test
	public void getTasks() {
		// Arrange
		Member member = memberRepository.save(MemberFixtures.create());
		Task task1 = taskRepository.save(TaskFixtures.createBy(member, "testTitle1", "testContent1"));
		Task task2 = taskRepository.save(TaskFixtures.createBy(member, "testTitle2", "testContent2"));

		// Act
		List<Task> tasks = taskService.getTasks(member.getId());

		// Assert
		assertThat(tasks).containsExactlyInAnyOrder(task1, task2);
	}

	@DisplayName("[정상] 할일 조회")
	@Test
	public void get() {
		// Arrange
		Member member = memberRepository.save(MemberFixtures.create());
		Task task = taskRepository.save(TaskFixtures.createBy(member, "testTitle1", "testContent1"));

		// Act
		Task foundTask = taskService.get(task.getId(), member.getId());

		// Assert
		assertThat(foundTask).isEqualTo(task);
	}

	@DisplayName("[정상] 사용자의 최근 수정된 할일 조회")
	@Test
	public void getRecentlyTask() {
		// Arrange
		Member member = memberRepository.save(MemberFixtures.create());
		Task task1 = taskRepository.save(TaskFixtures.createBy(member, "testTitle1", "testContent1"));
		Task task2 = taskRepository.save(TaskFixtures.createBy(member, "testTitle2", "testContent2"));

		taskService.change(member.getId(), task1.getId(), new UpdateTaskReqDto("testTitle2-1", "testContent2-1", TODO));

		// Act
		List<Task> tasks = taskService.getRecentlyTask(member.getId());

		// Assert
		assertThat(tasks).containsExactly(task1);
	}

	@DisplayName("[정상] 할일 저장")
	@Test
	public void save() {
		// Arrange
		CreateTaskReqDto dto = new CreateTaskReqDto("testTitle1", "testContent1", TODO);
		Long memberId = memberRepository.save(new Member()).getId();

		// Act
		CreateTaskResDto resDto = taskService.save(dto, memberId);

		// Assert
		assertThat(resDto).isNotNull();
		assertThat(taskRepository.findById(resDto.id())).isPresent();
	}

	@DisplayName("[정상] 할일 수정")
	@Test
	public void change() {
		// Arrange
		Member member = memberRepository.save(MemberFixtures.create());
		Task task = taskRepository.save(TaskFixtures.createBy(member, "testTitle1", "testContent1"));

		UpdateTaskReqDto dto = new UpdateTaskReqDto("Updated Title", "Updated Content", TODO);

		// Act
		UpdateTaskResDto resDto = taskService.change(member.getId(), task.getId(), dto);

		// Assert
		assertThat(resDto).isNotNull();
		assertThat(resDto.title()).isEqualTo(dto.title());
		assertThat(resDto.content()).isEqualTo(dto.content());
		assertThat(resDto.progress()).isEqualTo(dto.progress());
	}
}
