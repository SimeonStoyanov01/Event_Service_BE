package bg.tu.varna.events.api.model;

import bg.tu.varna.events.persistence.enums.ReportStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportModel {
	private UUID reportId;

	private ReportStatus status;

	private String description;

	private UUID referenceId;

	private String reason;

	private String reporterEmail;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime createdAt;

}
