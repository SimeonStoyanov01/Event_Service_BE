package bg.tu.varna.events.api.exceptions;

public class OrganizationAlreadyExistsException extends RuntimeException {

	public OrganizationAlreadyExistsException() {
		super("Organization with this name already exists");
	}
}
