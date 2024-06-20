package bg.tu.varna.events.api.operations.report.getall;

import bg.tu.varna.events.api.base.ProcessorRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllReportsRequest implements ProcessorRequest{

	@NotBlank(message = "Field must not be blank")
	private Boolean includeClosed;

}