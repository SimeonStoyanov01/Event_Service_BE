package bg.tu.varna.events.rest;

import bg.tu.varna.events.api.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getAllErrors().stream()
				.map(error -> {
					String field = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
					return field + ": " + error.getDefaultMessage();
				})
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.join("\n", errors));
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		logger.error("Unexpected error occurred", ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	public ResponseEntity<String> handleBadCredentialsException(AccessDeniedException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	}

	@ExceptionHandler(EmptyEventsListException.class)
	public ResponseEntity<String> handleEmptyEventsListException(EmptyEventsListException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(EventNotFoundException.class)
	public ResponseEntity<String> handleEventNotFoundException(EventNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(EventSuspendedException.class)
	public ResponseEntity<String> handleEventSuspendedException(EventSuspendedException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler(InvalidRefreshTokenException.class)
	public ResponseEntity<String> handleInvalidRefreshTokenException(InvalidRefreshTokenException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(OrganizationAlreadyExistsException.class)
	public ResponseEntity<String> handleOrganizationAlreadyExistsException(OrganizationAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler(PasswordsDoNotMatchException.class)
	public ResponseEntity<String> handlePasswordsDoNotMatchException(PasswordsDoNotMatchException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(TokenNotFoundException.class)
	public ResponseEntity<String> handleTokenNotFoundException(TokenNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(UnauthorizedActionException.class)
	public ResponseEntity<String> handleUnauthorizedActionException(UnauthorizedActionException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	}

	@ExceptionHandler(UserExistsException.class)
	public ResponseEntity<String> handleUserExistsException(UserExistsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
}