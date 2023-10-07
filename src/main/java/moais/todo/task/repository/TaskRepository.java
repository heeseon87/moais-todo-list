package moais.todo.task.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import moais.todo.task.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("SELECT t FROM Task t WHERE t.member.id = :memberId")
	List<Task> findAllMyTasks(Long memberId);

	@Query("SELECT t FROM Task t WHERE t.id = :taskId AND t.member.id = :memberId")
	Optional<Task> findMyTask(Long taskId, Long memberId);

	/**
	 * 최근 변경된 태스크 조회
	 */
	List<Task> findFirstByMember_IdOrderByUpdatedAtDesc(Long memberId);

	@Modifying
	@Query("DELETE FROM Task t WHERE t.member.id = :memberId")
	void deleteAllBy(Long memberId);

}
