package bg.tu.varna.events.rest.controller;

import bg.tu.varna.events.api.operations.reservation.cancel.CancelReservationOperation;
import bg.tu.varna.events.api.operations.reservation.cancel.CancelReservationRequest;
import bg.tu.varna.events.api.operations.reservation.cancel.CancelReservationResponse;
import bg.tu.varna.events.api.operations.reservation.create.CreateReservationOperation;
import bg.tu.varna.events.api.operations.reservation.create.CreateReservationRequest;
import bg.tu.varna.events.api.operations.reservation.create.CreateReservationResponse;
import bg.tu.varna.events.api.operations.reservation.delete.DeleteReservationOperation;
import bg.tu.varna.events.api.operations.reservation.delete.DeleteReservationRequest;
import bg.tu.varna.events.api.operations.reservation.delete.DeleteReservationResponse;
import bg.tu.varna.events.api.operations.reservation.get.GetReservationOperation;
import bg.tu.varna.events.api.operations.reservation.get.GetReservationRequest;
import bg.tu.varna.events.api.operations.reservation.get.GetReservationResponse;
import bg.tu.varna.events.api.operations.reservation.getmy.GetMyReservationsOperation;
import bg.tu.varna.events.api.operations.reservation.getmy.GetMyReservationsRequest;
import bg.tu.varna.events.api.operations.reservation.getmy.GetMyReservationsResponse;
import bg.tu.varna.events.api.operations.reservation.patch.PatchReservationOperation;
import bg.tu.varna.events.api.operations.reservation.patch.PatchReservationRequest;
import bg.tu.varna.events.api.operations.reservation.patch.PatchReservationResponse;
import bg.tu.varna.events.api.operations.reservation.pay.ChangePaymentStatusReservationOperation;
import bg.tu.varna.events.api.operations.reservation.pay.ChangePaymentStatusReservationRequest;
import bg.tu.varna.events.api.operations.reservation.pay.ChangePaymentStatusReservationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

	private final CreateReservationOperation createReservationOperation;
	private final DeleteReservationOperation deleteReservationOperation;
	private final GetMyReservationsOperation getMyReservationsOperation;
	private final GetReservationOperation getReservationOperation;
	private final PatchReservationOperation patchReservationOperation;
	private final CancelReservationOperation cancelReservationOperation;
	private final ChangePaymentStatusReservationOperation changePaymentStatusReservationOperation;

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('reservation:create')")
	public ResponseEntity<CreateReservationResponse> createReservation(@RequestBody @Valid CreateReservationRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(createReservationOperation.process(request));
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('reservation:delete')")
	public ResponseEntity<DeleteReservationResponse> deleteReservation(@RequestBody @Valid DeleteReservationRequest request) {
		return ResponseEntity.ok(deleteReservationOperation.process(request));
	}

	@GetMapping("/get_my")
	@PreAuthorize("hasAuthority('reservation:read-my-reservations')")
	public ResponseEntity<GetMyReservationsResponse> getMyReservations() {
		GetMyReservationsRequest request = GetMyReservationsRequest
				.builder()
				.build();
		return ResponseEntity.ok(getMyReservationsOperation.process(request));
	}

	@GetMapping("/get")
	@PreAuthorize("hasAuthority('reservation:read')")
	public ResponseEntity<GetReservationResponse> getReservation(@RequestParam("reservationId") @UUID String reservationId) {
		GetReservationRequest request = GetReservationRequest.builder()
				.reservationId(reservationId)
				.build();
		return ResponseEntity.ok(getReservationOperation.process(request));
	}

	@PatchMapping("/update")
	@PreAuthorize("hasAuthority('reservation:update')")
	public ResponseEntity<PatchReservationResponse> updateReservation(@RequestBody @Valid PatchReservationRequest request) {
		return ResponseEntity.ok(patchReservationOperation.process(request));
	}

	@PatchMapping("/cancel")
	@PreAuthorize("hasAuthority('reservation:cancel')")
	public ResponseEntity<CancelReservationResponse> cancelReservation(@RequestBody @Valid CancelReservationRequest request) {
		return ResponseEntity.ok(cancelReservationOperation.process(request));
	}

	@PatchMapping("/pay")
	@PreAuthorize("hasAuthority('reservation:pay')")
	public ResponseEntity<ChangePaymentStatusReservationResponse> payReservation(@RequestBody @Valid ChangePaymentStatusReservationRequest request) {
		return ResponseEntity.ok(changePaymentStatusReservationOperation.process(request));
	}
}