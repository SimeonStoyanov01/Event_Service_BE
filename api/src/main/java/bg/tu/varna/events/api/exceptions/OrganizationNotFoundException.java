package bg.tu.varna.events.api.exceptions;

public class OrganizationNotFoundException extends RuntimeException{

	public OrganizationNotFoundException() {
		super("No such organization found");
	}
}
