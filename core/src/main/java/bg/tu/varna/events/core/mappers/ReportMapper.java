package bg.tu.varna.events.core.mappers;

import bg.tu.varna.events.api.model.ReportModel;
import bg.tu.varna.events.persistence.entities.Report;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper implements Converter<Report, ReportModel> {

	@Override
	public ReportModel convert(Report source) {
		return ReportModel
				.builder()
				.reportId(source.getReportId())
				.status(source.getReportStatus())
				.createdAt(source.getCreatedAt())
				.description(source.getDescription())
				.reporterEmail(source.getReporterEmail())
				.referenceId(source.getReferenceId())
				.reason(source.getReason())
				.build();
	}
}
