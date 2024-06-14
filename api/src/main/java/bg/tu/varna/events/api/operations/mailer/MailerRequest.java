package bg.tu.varna.events.api.operations.mailer;

import bg.tu.varna.events.api.base.ProcessorRequest;
import lombok.*;

import java.util.Map;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailerRequest implements ProcessorRequest {
	private String to;
	private String subject;
	private String templateName;
	private Map<String, Object> templateModel;
}
