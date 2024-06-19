package bg.tu.varna.events.core.processors.report;

import bg.tu.varna.events.api.model.ReportModel;
import bg.tu.varna.events.api.operations.report.create.CreateReportOperation;
import bg.tu.varna.events.api.operations.report.create.CreateReportRequest;
import bg.tu.varna.events.api.operations.report.create.CreateReportResponse;
import bg.tu.varna.events.core.utils.ValidationUtils;
import bg.tu.varna.events.persistence.entities.Report;
import bg.tu.varna.events.persistence.entities.User;
import bg.tu.varna.events.persistence.enums.ReportStatus;
import bg.tu.varna.events.persistence.repositories.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateReportProcessor implements CreateReportOperation {
	private final ReportRepository reportRepository;
	private final ValidationUtils validationUtils;
	private final ConversionService conversionService;
	@Value("${client.url}")
	String clientUrl;
	@Override
	public CreateReportResponse process(CreateReportRequest request) {
		User user = validationUtils.getCurrentAuthenticatedUser();
		Report report = saveReport(request,user);


		ReportModel reportModel = conversionService.convert(report, ReportModel.class);

		return CreateReportResponse.builder()
				.reportModel(reportModel)
				.build();
	}

	private Report saveReport(CreateReportRequest request, User user) {
		Report report = Report.builder()
				.reportStatus(ReportStatus.PENDING_ANSWER)
				.description(request.getDescription())
				.referenceId(UUID.fromString(request.getReferenceId()))
				.reporterEmail(user.getEmail())
				.createdAt(LocalDateTime.now())
				.build();

		return reportRepository.save(report);
	}
}
