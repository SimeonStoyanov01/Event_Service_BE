package bg.tu.varna.events.core.processors.mail;

import bg.tu.varna.events.api.operations.mailer.MailerOperation;
import bg.tu.varna.events.api.operations.mailer.MailerRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@Async
public class MailerProcessor implements MailerOperation {
	private final JavaMailSender mailSender;
	private final SpringTemplateEngine templateEngine;
	@Override
	public void process(MailerRequest request) {
		Context context = new Context();
		context.setVariables(request.getTemplateModel());
		String htmlContent = templateEngine.process(request.getTemplateName(), context);

		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(request.getTo());
			helper.setSubject(request.getSubject());
			helper.setText(htmlContent, true);
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send email", e);
		}

		mailSender.send(message);
	}

}
