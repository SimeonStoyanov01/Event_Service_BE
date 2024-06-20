package bg.tu.varna.events.core.processors.report;

import bg.tu.varna.events.api.exceptions.ReportNotFoundException;
import bg.tu.varna.events.api.model.ReportModel;
import bg.tu.varna.events.api.operations.report.get.GetReportOperation;
import bg.tu.varna.events.api.operations.report.get.GetReportRequest;
import bg.tu.varna.events.api.operations.report.get.GetReportResponse;
import bg.tu.varna.events.persistence.entities.Report;
import bg.tu.varna.events.persistence.repositories.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetReportProcessor implements GetReportOperation {
	private final ReportRepository reportRepository;
	private final ConversionService conversionService;

	@Override
	public GetReportResponse process(GetReportRequest request) {
		Report report = reportRepository.findById(UUID.fromString(request.getReportId()))
				.orElseThrow(ReportNotFoundException::new);

		ReportModel reportModel = conversionService.convert(report, ReportModel.class);
		return GetReportResponse
				.builder()
				.reportModel(reportModel)
				.build();
	}
}
