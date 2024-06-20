package bg.tu.varna.events.api.exceptions;

public class ReportClosedException extends RuntimeException {

	public ReportClosedException() {
		super("Report has been closed");
	}
}
