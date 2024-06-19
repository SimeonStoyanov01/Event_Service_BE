package bg.tu.varna.events.api.operations.report.answer;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerReportRequest implements ProcessorRequest{

	@NotBlank(message = "Report id is required")
	private String reportId;

	@NotBlank(message = "Please, provide an answer")
	private String reason;

}