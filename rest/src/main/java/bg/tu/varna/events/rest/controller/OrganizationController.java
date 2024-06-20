package bg.tu.varna.events.rest.controller;

import bg.tu.varna.events.api.operations.organization.delete.DeleteOrganizationOperation;
import bg.tu.varna.events.api.operations.organization.delete.DeleteOrganizationRequest;
import bg.tu.varna.events.api.operations.organization.delete.DeleteOrganizationResponse;
import bg.tu.varna.events.api.operations.organization.get.GetOrganizationOperation;
import bg.tu.varna.events.api.operations.organization.get.GetOrganizationRequest;
import bg.tu.varna.events.api.operations.organization.get.GetOrganizationResponse;
import bg.tu.varna.events.api.operations.organization.getaccumulated.GetOrganizationAccumulatedEarningsOperation;
import bg.tu.varna.events.api.operations.organization.getaccumulated.GetOrganizationAccumulatedEarningsRequest;
import bg.tu.varna.events.api.operations.organization.getaccumulated.GetOrganizationAccumulatedEarningsResponse;
import bg.tu.varna.events.api.operations.organization.getearnings.GetOrganizationEarningsOperation;
import bg.tu.varna.events.api.operations.organization.getearnings.GetOrganizationEarningsRequest;
import bg.tu.varna.events.api.operations.organization.getearnings.GetOrganizationEarningsResponse;
import bg.tu.varna.events.api.operations.organization.getemployees.GetOrganizationEmployeesOperation;
import bg.tu.varna.events.api.operations.organization.getemployees.GetOrganizationEmployeesRequest;
import bg.tu.varna.events.api.operations.organization.getemployees.GetOrganizationEmployeesResponse;
import bg.tu.varna.events.api.operations.organization.getsubscriptions.GetOrganizationSubscriptionsOperation;
import bg.tu.varna.events.api.operations.organization.getsubscriptions.GetOrganizationSubscriptionsRequest;
import bg.tu.varna.events.api.operations.organization.getsubscriptions.GetOrganizationSubscriptionsResponse;
import bg.tu.varna.events.api.operations.organization.patch.PatchOrganizationOperation;
import bg.tu.varna.events.api.operations.organization.patch.PatchOrganizationRequest;
import bg.tu.varna.events.api.operations.organization.patch.PatchOrganizationResponse;
import bg.tu.varna.events.api.operations.organization.suspend.SuspendOrganizationOperation;
import bg.tu.varna.events.api.operations.organization.suspend.SuspendOrganizationRequest;
import bg.tu.varna.events.api.operations.organization.suspend.SuspendOrganizationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/organization")
public class OrganizationController  {
	private final GetOrganizationOperation getOrganizationOperation;
	private final PatchOrganizationOperation patchOrganizationOperation;
	private final GetOrganizationSubscriptionsOperation getOrganizationSubscriptionsOperation;
	private final GetOrganizationEmployeesOperation getOrganizationEmployeesOperation;
	private final DeleteOrganizationOperation deleteOrganizationOperation;
	private final SuspendOrganizationOperation suspendOrganizationOperation;
	private final GetOrganizationEarningsOperation getOrganizationEarningsOperation;
	private final GetOrganizationAccumulatedEarningsOperation getOrganizationAccumulatedEarningsOperation;
	@GetMapping("/get")
	public ResponseEntity<GetOrganizationResponse> getEvent(@RequestParam("organizationId") @UUID String organizationId) {
		GetOrganizationRequest request = GetOrganizationRequest
				.builder()
				.organizationId(organizationId)
				.build();
		return ResponseEntity.ok(getOrganizationOperation.process(request));
	}

	@GetMapping("/get_employees")
	@PreAuthorize("hasAuthority('organization:read-employees')")
	public ResponseEntity<GetOrganizationEmployeesResponse> getOrganizationEmployees(@RequestParam("organizationId") @UUID String organizationId) {
		GetOrganizationEmployeesRequest request = GetOrganizationEmployeesRequest
				.builder()
				.organizationId(organizationId)
				.build();
		return ResponseEntity.ok(getOrganizationEmployeesOperation.process(request));
	}

	@GetMapping("/get_earnings")
	@PreAuthorize("hasAuthority('organization:read-earnings')")
	public ResponseEntity<GetOrganizationEarningsResponse> getOrganizationEarningsPerEvents(@RequestParam("organizationId") String organizationId) {
		GetOrganizationEarningsRequest request = GetOrganizationEarningsRequest
				.builder()
				.organizationId(organizationId)
				.build();
		return ResponseEntity.ok(getOrganizationEarningsOperation.process(request));
	}
	@GetMapping("/get_accumulated")
	@PreAuthorize("hasAuthority('organization:read-earnings')")
	public ResponseEntity<GetOrganizationAccumulatedEarningsResponse> getOrganizationAccumulatedEarnings(@RequestParam("organizationId") String organizationId) {
		GetOrganizationAccumulatedEarningsRequest request = GetOrganizationAccumulatedEarningsRequest
				.builder()
				.organizationId(organizationId)
				.build();
		return ResponseEntity.ok(getOrganizationAccumulatedEarningsOperation.process(request));
	}
	@GetMapping("/get_subscriptions")
	@PreAuthorize("hasAuthority('organization:read-subscriptions')")
	public ResponseEntity<GetOrganizationSubscriptionsResponse> getOrganizationSubscriptions(@RequestParam("organizationId") String organizationId) {
		GetOrganizationSubscriptionsRequest request = GetOrganizationSubscriptionsRequest
				.builder()
				.organizationId(organizationId)
				.build();
		return ResponseEntity.ok(getOrganizationSubscriptionsOperation.process(request));
	}
	@PatchMapping("/suspend")
	@PreAuthorize("hasAuthority('organization:suspend')")
	public ResponseEntity<SuspendOrganizationResponse> suspendOrganization(@RequestBody @Valid SuspendOrganizationRequest request) {
		return ResponseEntity.ok(suspendOrganizationOperation.process(request));
	}

	@PatchMapping("/update")
	@PreAuthorize("hasAuthority('organization:update')")
	public ResponseEntity<PatchOrganizationResponse> updateOrganization(@RequestBody @Valid PatchOrganizationRequest request) {
		return ResponseEntity.ok(patchOrganizationOperation.process(request));
	}


	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('organization:delete')")
	public ResponseEntity<DeleteOrganizationResponse> deleteEvent(@RequestBody @Valid DeleteOrganizationRequest request) {
		return ResponseEntity.ok(deleteOrganizationOperation.process(request));
	}

}
