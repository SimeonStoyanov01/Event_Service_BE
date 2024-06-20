package bg.tu.varna.events.rest.controller;

import bg.tu.varna.events.api.operations.report.answer.AnswerReportOperation;
import bg.tu.varna.events.api.operations.report.answer.AnswerReportRequest;
import bg.tu.varna.events.api.operations.report.answer.AnswerReportResponse;
import bg.tu.varna.events.api.operations.report.create.CreateReportOperation;
import bg.tu.varna.events.api.operations.report.create.CreateReportRequest;
import bg.tu.varna.events.api.operations.report.create.CreateReportResponse;
import bg.tu.varna.events.api.operations.report.delete.DeleteReportOperation;
import bg.tu.varna.events.api.operations.report.delete.DeleteReportRequest;
import bg.tu.varna.events.api.operations.report.delete.DeleteReportResponse;
import bg.tu.varna.events.api.operations.report.get.GetReportOperation;
import bg.tu.varna.events.api.operations.report.get.GetReportRequest;
import bg.tu.varna.events.api.operations.report.get.GetReportResponse;
import bg.tu.varna.events.api.operations.report.getall.GetAllReportsOperation;
import bg.tu.varna.events.api.operations.report.getall.GetAllReportsRequest;
import bg.tu.varna.events.api.operations.report.getall.GetAllReportsResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController {
	private final GetReportOperation getReportOperation;
	private final GetAllReportsOperation getAllReportsOperation;
	private final CreateReportOperation createReportOperation;
	private final DeleteReportOperation deleteReportOperation;
	private final AnswerReportOperation answerReportOperation;

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('report:create')")
	public ResponseEntity<CreateReportResponse> createReport(@RequestBody CreateReportRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(createReportOperation.process(request));
	}

	@GetMapping("/get")
	@PreAuthorize("hasAuthority('report:read')")
	public ResponseEntity<GetReportResponse> getReport(@RequestParam("reportId") @UUID String reportId) {
		GetReportRequest request = GetReportRequest
				.builder()
				.reportId(reportId)
				.build();
		return ResponseEntity.ok(getReportOperation.process(request));
	}

	@GetMapping("/get-all")
	@PreAuthorize("hasAuthority('report:read-all')")
	public ResponseEntity<GetAllReportsResponse> getAllReports(@RequestParam("includeClosed") Boolean includeClosed) {
		GetAllReportsRequest request = GetAllReportsRequest
				.builder()
				.includeClosed(includeClosed)
				.build();
		return ResponseEntity.ok(getAllReportsOperation.process(request));
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('report:delete')")
	public ResponseEntity<DeleteReportResponse> deleteReport(@RequestBody DeleteReportRequest request) {
		return ResponseEntity.ok(deleteReportOperation.process(request));
	}

	@PatchMapping("/answer")
	@PreAuthorize("hasAuthority('report:answer')")
	public ResponseEntity<AnswerReportResponse> answerReport(@RequestBody AnswerReportRequest request) {
		return ResponseEntity.ok(answerReportOperation.process(request));
	}

}
