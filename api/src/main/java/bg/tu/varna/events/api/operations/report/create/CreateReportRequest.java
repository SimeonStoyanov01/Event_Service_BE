package bg.tu.varna.events.api.operations.report.create;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReportRequest implements ProcessorRequest {

	@NotBlank(message = "Description is required")
	private String description;

	@NotBlank(message = "Reference id is required")
	private String referenceId;
}