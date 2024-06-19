package bg.tu.varna.events.core.processors.report;

import bg.tu.varna.events.api.exceptions.ReportClosedException;
import bg.tu.varna.events.api.exceptions.ReportNotFoundException;
import bg.tu.varna.events.api.model.ReportModel;
import bg.tu.varna.events.api.operations.mailer.MailerOperation;
import bg.tu.varna.events.api.operations.mailer.MailerRequest;
import bg.tu.varna.events.api.operations.report.answer.AnswerReportOperation;
import bg.tu.varna.events.api.operations.report.answer.AnswerReportRequest;
import bg.tu.varna.events.api.operations.report.answer.AnswerReportResponse;
import bg.tu.varna.events.core.utils.PartialUpdateUtils;
import bg.tu.varna.events.persistence.entities.Report;
import bg.tu.varna.events.persistence.enums.ReportStatus;
import bg.tu.varna.events.persistence.repositories.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerReportProcessor implements AnswerReportOperation {
	private final ReportRepository reportRepository;
	private final ConversionService conversionService;
	private final PartialUpdateUtils partialUpdateUtils;
	private final MailerOperation mailerOperation;
	private static final Set<String> EXCLUDED_FIELDS = Set.of("reportId");

	@Override
	public AnswerReportResponse process(AnswerReportRequest request) {
		Report report = reportRepository.findById(UUID.fromString(request.getReportId()))
				.orElseThrow(ReportNotFoundException::new);

		if(report.getReportStatus().equals(ReportStatus.CLOSED))
			throw new ReportClosedException();

		report.setReportStatus(ReportStatus.CLOSED);
		partialUpdateUtils.updateFields(report,request,EXCLUDED_FIELDS);
		Report savedReport = reportRepository.save(report);

		ReportModel reportModel = conversionService.convert(savedReport, ReportModel.class);

		sendEmail(savedReport);

		return AnswerReportResponse
				.builder()
				.reportModel(reportModel)
				.build();
	}
	private void sendEmail(Report report) {
		Map<String, Object> templateModel = new HashMap<>();
		templateModel.put("name", report.getReporterEmail());
		templateModel.put("report", report.getReportId());
		templateModel.put("reason", report.getReason());
		templateModel.put("description",report.getDescription());

		MailerRequest mailerRequest = MailerRequest.builder()
				.to(report.getReporterEmail())
				.subject("Closed report " + report.getReportId())
				.templateName("answerReportTemplate")
				.templateModel(templateModel)
				.build();

		mailerOperation.process(mailerRequest);
	}
}
