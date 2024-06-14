package bg.tu.varna.events.rest.controller;

import bg.tu.varna.events.api.operations.subscription.cancel.CancelSubscriptionOperation;
import bg.tu.varna.events.api.operations.subscription.cancel.CancelSubscriptionRequest;
import bg.tu.varna.events.api.operations.subscription.cancel.CancelSubscriptionResponse;
import bg.tu.varna.events.api.operations.subscription.create.CreateSubscriptionOperation;
import bg.tu.varna.events.api.operations.subscription.create.CreateSubscriptionRequest;
import bg.tu.varna.events.api.operations.subscription.create.CreateSubscriptionResponse;
import bg.tu.varna.events.api.operations.subscription.get.GetSubscriptionOperation;
import bg.tu.varna.events.api.operations.subscription.get.GetSubscriptionRequest;
import bg.tu.varna.events.api.operations.subscription.get.GetSubscriptionResponse;
import bg.tu.varna.events.api.operations.subscription.getmy.GetMySubscriptionsOperation;
import bg.tu.varna.events.api.operations.subscription.getmy.GetMySubscriptionsRequest;
import bg.tu.varna.events.api.operations.subscription.getmy.GetMySubscriptionsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
	private final CreateSubscriptionOperation createSubscriptionOperation;
	private final CancelSubscriptionOperation cancelSubscriptionOperation;
	private final GetMySubscriptionsOperation getMySubscriptionsOperation;
	private final GetSubscriptionOperation getSubscriptionOperation;

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('subscription:create')")
	public ResponseEntity<CreateSubscriptionResponse> createSubscription(@RequestBody @Valid CreateSubscriptionRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(createSubscriptionOperation.process(request));
	}

	@PatchMapping("/cancel")
	@PreAuthorize("hasAuthority('subscription:cancel')")
	public ResponseEntity<CancelSubscriptionResponse> cancelSubscription(@RequestBody @Valid CancelSubscriptionRequest request) {
		return ResponseEntity.ok(cancelSubscriptionOperation.process(request));
	}
	@GetMapping("/get_my")
	@PreAuthorize("hasAuthority('subscription:read-my-subscriptions')")
	public ResponseEntity<GetMySubscriptionsResponse> getMySubscriptions() {
		GetMySubscriptionsRequest request = GetMySubscriptionsRequest
				.builder()
				.build();
		return ResponseEntity.ok(getMySubscriptionsOperation.process(request));
	}

	@GetMapping("/get")
	@PreAuthorize("hasAuthority('subscription:read')")
	public ResponseEntity<GetSubscriptionResponse> getSubscription(@RequestParam("subscriptionId") @UUID String subscriptionId) {
		GetSubscriptionRequest request = GetSubscriptionRequest
				.builder()
				.subscriptionId(subscriptionId)
				.build();
		return ResponseEntity.ok(getSubscriptionOperation.process(request));
	}



}
