package bg.tu.varna.events.api.operations.report.getall;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.ReportModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllReportsResponse implements ProcessorResponse {
	private List<ReportModel> reportModel;
}

