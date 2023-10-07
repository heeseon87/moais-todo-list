package moais.todo.error;

import lombok.Getter;

@Getter
public class ApiError {

	private String message;

	public ApiError(String message) {
		this.message = message;
	}
}
