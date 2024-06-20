package bg.tu.varna.events.api.operations.report.get;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetReportRequest implements ProcessorRequest{

	@NotBlank(message = "Report id is required")
	private String reportId;

}