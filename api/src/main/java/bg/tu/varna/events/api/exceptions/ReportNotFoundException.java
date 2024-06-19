package bg.tu.varna.events.api.exceptions;

public class ReportNotFoundException extends RuntimeException{

	public ReportNotFoundException() {
		super("Report not found");
	}
}
