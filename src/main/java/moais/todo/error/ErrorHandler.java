package moais.todo.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiError> handle(RuntimeException e) {
		ApiError apiError = new ApiError(e.getMessage());
		return ResponseEntity.badRequest().body(apiError);
	}

}
