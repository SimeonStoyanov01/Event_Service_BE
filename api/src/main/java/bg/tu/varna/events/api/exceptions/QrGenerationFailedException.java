package bg.tu.varna.events.api.exceptions;

public class QrGenerationFailedException extends RuntimeException {

	public QrGenerationFailedException() {
		super("Qr generator failed");
	}
}
