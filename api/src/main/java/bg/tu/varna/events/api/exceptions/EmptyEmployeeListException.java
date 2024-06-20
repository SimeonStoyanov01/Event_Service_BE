package bg.tu.varna.events.api.exceptions;

public class EmptyEmployeeListException extends RuntimeException {

	public EmptyEmployeeListException() {
		super("No employees in the organization");
	}
}
