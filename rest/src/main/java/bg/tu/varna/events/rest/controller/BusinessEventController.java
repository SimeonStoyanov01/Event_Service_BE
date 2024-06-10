package bg.tu.varna.events.rest.controller;

import bg.tu.varna.events.api.operations.businessevent.create.CreateEventOperation;
import bg.tu.varna.events.api.operations.businessevent.create.CreateEventRequest;
import bg.tu.varna.events.api.operations.businessevent.create.CreateEventResponse;
import bg.tu.varna.events.api.operations.businessevent.delete.DeleteEventOperation;
import bg.tu.varna.events.api.operations.businessevent.delete.DeleteEventRequest;
import bg.tu.varna.events.api.operations.businessevent.delete.DeleteEventResponse;
import bg.tu.varna.events.api.operations.businessevent.get.GetEventOperation;
import bg.tu.varna.events.api.operations.businessevent.get.GetEventRequest;
import bg.tu.varna.events.api.operations.businessevent.get.GetEventResponse;
import bg.tu.varna.events.api.operations.businessevent.getall.GetAllEventsOperation;
import bg.tu.varna.events.api.operations.businessevent.getall.GetAllEventsRequest;
import bg.tu.varna.events.api.operations.businessevent.getall.GetAllEventsResponse;
import bg.tu.varna.events.api.operations.businessevent.getbyorganization.GetAllEventsByOrgOperation;
import bg.tu.varna.events.api.operations.businessevent.getbyorganization.GetAllEventsByOrgRequest;
import bg.tu.varna.events.api.operations.businessevent.getbyorganization.GetAllEventsByOrgResponse;
import bg.tu.varna.events.api.operations.businessevent.getmy.GetMyEventsOperation;
import bg.tu.varna.events.api.operations.businessevent.getmy.GetMyEventsRequest;
import bg.tu.varna.events.api.operations.businessevent.getmy.GetMyEventsResponse;
import bg.tu.varna.events.api.operations.businessevent.patch.PatchEventOperation;
import bg.tu.varna.events.api.operations.businessevent.patch.PatchEventRequest;
import bg.tu.varna.events.api.operations.businessevent.patch.PatchEventResponse;
import bg.tu.varna.events.api.operations.businessevent.suspend.SuspendEventOperation;
import bg.tu.varna.events.api.operations.businessevent.suspend.SuspendEventRequest;
import bg.tu.varna.events.api.operations.businessevent.suspend.SuspendEventResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/business-event")
public class BusinessEventController {

	private final CreateEventOperation createEventOperation;
	private final DeleteEventOperation deleteEventOperation;
	private final GetEventOperation getEventOperation;
	private final GetAllEventsOperation getAllEventsOperation;
	private final GetAllEventsByOrgOperation getAllEventsByOrgOperation;
	private final GetMyEventsOperation getMyEventsOperation;
	private final SuspendEventOperation suspendEventOperation;
	private final PatchEventOperation patchEventOperation;

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('business_event:create')")
	public ResponseEntity<CreateEventResponse> createEvent(@RequestBody @Valid CreateEventRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(createEventOperation.process(request));
	}

	@GetMapping("/get")
	public ResponseEntity<GetEventResponse> getEvent(@RequestParam("eventId") @UUID String eventId) {
		GetEventRequest request = GetEventRequest
				.builder()
				.eventId(eventId)
				.build();
		return ResponseEntity.ok(getEventOperation.process(request));
	}

	@GetMapping("/get_all")
	public ResponseEntity<GetAllEventsResponse> getAllEvents(@RequestParam("includeSuspended") Boolean includeSuspended) {
		GetAllEventsRequest request = GetAllEventsRequest
				.builder()
				.includeSuspended(includeSuspended)
				.build();
		return ResponseEntity.ok(getAllEventsOperation.process(request));
	}

	@GetMapping("/get_all_by_organization")
	public ResponseEntity<GetAllEventsByOrgResponse> getAllEventsBYOrg(
			@RequestParam("organizationId") String organizationId,
			@RequestParam("includeSuspended") Boolean includeSuspended) {
		GetAllEventsByOrgRequest request = GetAllEventsByOrgRequest
				.builder()
				.organizationId(organizationId)
				.includeSuspended(includeSuspended)
				.build();
		return ResponseEntity.ok(getAllEventsByOrgOperation.process(request));
	}
	@GetMapping("/get_my")
	@PreAuthorize("hasAuthority('business_event:read-by-user')")
	public ResponseEntity<GetMyEventsResponse> getMyEvents(@RequestParam("includeSuspended") Boolean includeSuspended) {
		GetMyEventsRequest request = GetMyEventsRequest
				.builder()
				.includeSuspended(includeSuspended)
				.build();
		return ResponseEntity.ok(getMyEventsOperation.process(request));
	}

	@PatchMapping("/suspend")
	@PreAuthorize("hasAuthority('business_event:suspend')")
	public ResponseEntity<SuspendEventResponse> suspendEvent(@RequestBody @Valid SuspendEventRequest request) {
		return ResponseEntity.ok(suspendEventOperation.process(request));
	}

	@PatchMapping("/update")
	@PreAuthorize("hasAuthority('business_event:update')")
	public ResponseEntity<PatchEventResponse> suspendEvent(@RequestBody @Valid PatchEventRequest request) {
		return ResponseEntity.ok(patchEventOperation.process(request));
	}


	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('business_event:delete')")
	public ResponseEntity<DeleteEventResponse> deleteEvent(@RequestBody @Valid DeleteEventRequest request) {
		return ResponseEntity.ok(deleteEventOperation.process(request));
	}

}
