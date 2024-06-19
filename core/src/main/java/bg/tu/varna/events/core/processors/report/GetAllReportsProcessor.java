package bg.tu.varna.events.core.processors.report;

import bg.tu.varna.events.api.model.ReportModel;
import bg.tu.varna.events.api.operations.report.getall.GetAllReportsOperation;
import bg.tu.varna.events.api.operations.report.getall.GetAllReportsRequest;
import bg.tu.varna.events.api.operations.report.getall.GetAllReportsResponse;
import bg.tu.varna.events.persistence.enums.ReportStatus;
import bg.tu.varna.events.persistence.repositories.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllReportsProcessor implements GetAllReportsOperation {
	private final ReportRepository reportRepository;
	private final ConversionService conversionService;

	@Override
	public GetAllReportsResponse process(GetAllReportsRequest request) {
		List<ReportModel> reportList = (request.getIncludeClosed()
				?reportRepository.findAll()
				:reportRepository.findAllByReportStatus(ReportStatus.PENDING_ANSWER))
				.stream()
				.map(report -> conversionService.convert(report, ReportModel.class))
				.toList();

		return GetAllReportsResponse
				.builder()
				.reportModel(reportList)
				.build();
	}
}
