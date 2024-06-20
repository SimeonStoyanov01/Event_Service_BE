package bg.tu.varna.events.api.exceptions;

public class MenuNotFoundException extends RuntimeException{

	public MenuNotFoundException() {
		super("Menu not found");
	}
}
