package bg.tu.varna.events.api.operations.report.delete;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteReportRequest implements ProcessorRequest{

	@NotBlank(message = "Report id is required")
	private String reportId;

}