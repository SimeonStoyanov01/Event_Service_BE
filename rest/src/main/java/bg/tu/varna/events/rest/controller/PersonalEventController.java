package bg.tu.varna.events.rest.controller;

import bg.tu.varna.events.api.operations.personalevent.create.CreatePersonalEventOperation;
import bg.tu.varna.events.api.operations.personalevent.create.CreatePersonalEventRequest;
import bg.tu.varna.events.api.operations.personalevent.create.CreatePersonalEventResponse;
import bg.tu.varna.events.api.operations.personalevent.delete.DeletePersonalEventOperation;
import bg.tu.varna.events.api.operations.personalevent.delete.DeletePersonalEventRequest;
import bg.tu.varna.events.api.operations.personalevent.delete.DeletePersonalEventResponse;
import bg.tu.varna.events.api.operations.personalevent.get.GetPersonalEventOperation;
import bg.tu.varna.events.api.operations.personalevent.get.GetPersonalEventRequest;
import bg.tu.varna.events.api.operations.personalevent.get.GetPersonalEventResponse;
import bg.tu.varna.events.api.operations.personalevent.getall.GetMyPersonalEventsOperation;
import bg.tu.varna.events.api.operations.personalevent.getall.GetMyPersonalEventsRequest;
import bg.tu.varna.events.api.operations.personalevent.getall.GetMyPersonalEventsResponse;
import bg.tu.varna.events.api.operations.personalevent.patch.PatchPersonalEventOperation;
import bg.tu.varna.events.api.operations.personalevent.patch.PatchPersonalEventRequest;
import bg.tu.varna.events.api.operations.personalevent.patch.PatchPersonalEventResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/personal-event")
@PreAuthorize("hasAnyRole('PERSONAL','ADMIN')")
public class PersonalEventController {
	private final CreatePersonalEventOperation createPersonalEventOperation;
	private final DeletePersonalEventOperation deletePersonalEventOperation;
	private final GetPersonalEventOperation getPersonalEventOperation;
	private final PatchPersonalEventOperation patchPersonalEventOperation;
	private final GetMyPersonalEventsOperation getMyPersonalEventsOperation;
	@PostMapping("/create")
	@PreAuthorize("hasAuthority('personal_event:create')")
	public ResponseEntity<CreatePersonalEventResponse> createEvent(@RequestBody @Valid CreatePersonalEventRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(createPersonalEventOperation.process(request));
	}
	@PatchMapping("/update")
	@PreAuthorize("hasAuthority('personal_event:update')")
	public ResponseEntity<PatchPersonalEventResponse> updateEvent(@RequestBody @Valid PatchPersonalEventRequest request) {
		return ResponseEntity.ok(patchPersonalEventOperation.process(request));
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('personal_event:delete')")
	public ResponseEntity<DeletePersonalEventResponse> deleteEvent(@RequestBody @Valid DeletePersonalEventRequest request) {
		return ResponseEntity.ok(deletePersonalEventOperation.process(request));
	}

	@GetMapping("/get")
	@PreAuthorize("hasAuthority('personal_event:read')")
	public ResponseEntity<GetPersonalEventResponse> getEvent(@RequestParam("personalEventId") @UUID String personalEventId) {
		GetPersonalEventRequest request = GetPersonalEventRequest
				.builder()
				.personalEventId(personalEventId)
				.build();
		return ResponseEntity.ok(getPersonalEventOperation.process(request));
	}

	@GetMapping("/get_my")
	@PreAuthorize("hasAuthority('personal_event:read-all')")
	public ResponseEntity<GetMyPersonalEventsResponse> getAllEvents() {
		GetMyPersonalEventsRequest request = GetMyPersonalEventsRequest
				.builder()
				.build();
		return ResponseEntity.ok(getMyPersonalEventsOperation.process(request));
	}

}
