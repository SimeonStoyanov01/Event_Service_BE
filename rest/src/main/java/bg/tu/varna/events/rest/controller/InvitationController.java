package bg.tu.varna.events.rest.controller;

import bg.tu.varna.events.api.operations.invitation.answer.AnswerInvitationOperation;
import bg.tu.varna.events.api.operations.invitation.answer.AnswerInvitationRequest;
import bg.tu.varna.events.api.operations.invitation.answer.AnswerInvitationResponse;
import bg.tu.varna.events.api.operations.invitation.create.CreateInvitationOperation;
import bg.tu.varna.events.api.operations.invitation.create.CreateInvitationRequest;
import bg.tu.varna.events.api.operations.invitation.create.CreateInvitationResponse;
import bg.tu.varna.events.api.operations.invitation.delete.DeleteInvitationOperation;
import bg.tu.varna.events.api.operations.invitation.delete.DeleteInvitationRequest;
import bg.tu.varna.events.api.operations.invitation.delete.DeleteInvitationResponse;
import bg.tu.varna.events.api.operations.invitation.get.GetInvitationOperation;
import bg.tu.varna.events.api.operations.invitation.get.GetInvitationRequest;
import bg.tu.varna.events.api.operations.invitation.get.GetInvitationResponse;
import bg.tu.varna.events.api.operations.invitation.getmy.GetMyInvitationsOperation;
import bg.tu.varna.events.api.operations.invitation.getmy.GetMyInvitationsRequest;
import bg.tu.varna.events.api.operations.invitation.getmy.GetMyInvitationsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/invitation")
public class InvitationController {

	private final CreateInvitationOperation createInvitationOperation;
	private final GetInvitationOperation getInvitationOperation;
	private final GetMyInvitationsOperation getMyInvitationsOperation;
	private final DeleteInvitationOperation deleteInvitationOperation;
	private final AnswerInvitationOperation answerInvitationOperation;

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('invitation:create')")
	public ResponseEntity<CreateInvitationResponse> createInvitation(@RequestBody @Valid CreateInvitationRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(createInvitationOperation.process(request));
	}

	@GetMapping("/get")
	@PreAuthorize("hasAuthority('invitation:read')")
	public ResponseEntity<GetInvitationResponse> getInvitation(@RequestParam("invitationId") @UUID String invitationId) {
		GetInvitationRequest request = GetInvitationRequest
				.builder()
				.invitationId(invitationId)
				.build();
		return ResponseEntity.ok(getInvitationOperation.process(request));
	}

	@GetMapping("/get-my")
	@PreAuthorize("hasAuthority('invitation:read-my-invitations')")
	public ResponseEntity<GetMyInvitationsResponse> getMyInvitations(@RequestParam("personalEventId") @UUID String personalEventId) {
		GetMyInvitationsRequest request = GetMyInvitationsRequest
				.builder()
				.personalEventId(personalEventId)
				.build();
		return ResponseEntity.ok(getMyInvitationsOperation.process(request));
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('invitation:delete')")
	public ResponseEntity<DeleteInvitationResponse> deleteInvitation(@RequestBody @Valid DeleteInvitationRequest request) {
		return ResponseEntity.ok(deleteInvitationOperation.process(request));
	}

	@PatchMapping("/answer")
	public ResponseEntity<AnswerInvitationResponse> answerInvitation(@RequestBody @Valid AnswerInvitationRequest request) {
		return ResponseEntity.ok(answerInvitationOperation.process(request));
	}
}