package bg.tu.varna.events.api.operations.report.delete;

import bg.tu.varna.events.api.base.ProcessorResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteReportResponse implements ProcessorResponse {
	private String message;
}

