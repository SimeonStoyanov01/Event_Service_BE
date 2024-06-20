package bg.tu.varna.events.api.exceptions;

public class PersonalEventNotFoundException extends RuntimeException{

	public PersonalEventNotFoundException() {
		super("Personal event not found");
	}
}
