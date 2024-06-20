package bg.tu.varna.events.core.processors.report;

import bg.tu.varna.events.api.exceptions.ReportNotFoundException;
import bg.tu.varna.events.api.operations.report.delete.DeleteReportOperation;
import bg.tu.varna.events.api.operations.report.delete.DeleteReportRequest;
import bg.tu.varna.events.api.operations.report.delete.DeleteReportResponse;
import bg.tu.varna.events.persistence.entities.Report;
import bg.tu.varna.events.persistence.repositories.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteReportProcessor implements DeleteReportOperation {

	private final ReportRepository reportRepository;

	@Override
	public DeleteReportResponse process(DeleteReportRequest request) {
		Report report = reportRepository.findById(UUID.fromString(request.getReportId()))
				.orElseThrow(ReportNotFoundException::new);

		reportRepository.delete(report);

		return DeleteReportResponse
				.builder()
				.message("Report deleted successfully")
				.build();
	}
}
