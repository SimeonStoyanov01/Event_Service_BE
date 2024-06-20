package bg.tu.varna.events.api.operations.report.create;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.ReportModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReportResponse implements ProcessorResponse {
	private ReportModel reportModel;
}

