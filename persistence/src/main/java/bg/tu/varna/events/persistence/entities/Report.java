package bg.tu.varna.events.persistence.entities;

import bg.tu.varna.events.persistence.enums.ReportStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reports")
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "report_id")
	private UUID reportId;

	@Column(name = "reporter_email", nullable = false)
	private String reporterEmail;

	@Enumerated(EnumType.STRING)
	@Column(name = "report_status", nullable = false)
	private ReportStatus reportStatus;

	@Column(name = "created_at", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "reference_id")
	private UUID referenceId;

	@Column(name = "reason")
	private String reason;

}
