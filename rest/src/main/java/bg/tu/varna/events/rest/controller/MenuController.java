package bg.tu.varna.events.rest.controller;

import bg.tu.varna.events.api.operations.menu.create.CreateMenuOperation;
import bg.tu.varna.events.api.operations.menu.create.CreateMenuRequest;
import bg.tu.varna.events.api.operations.menu.create.CreateMenuResponse;
import bg.tu.varna.events.api.operations.menu.delete.DeleteMenuOperation;
import bg.tu.varna.events.api.operations.menu.delete.DeleteMenuRequest;
import bg.tu.varna.events.api.operations.menu.delete.DeleteMenuResponse;
import bg.tu.varna.events.api.operations.menu.get.GetMenuOperation;
import bg.tu.varna.events.api.operations.menu.get.GetMenuRequest;
import bg.tu.varna.events.api.operations.menu.get.GetMenuResponse;
import bg.tu.varna.events.api.operations.menu.getall.GetAllMenusPerEventOperation;
import bg.tu.varna.events.api.operations.menu.getall.GetAllMenusPerEventRequest;
import bg.tu.varna.events.api.operations.menu.getall.GetAllMenusPerEventResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu")
public class MenuController {

	private final CreateMenuOperation createMenuOperation;
	private final DeleteMenuOperation deleteMenuOperation;
	private final GetAllMenusPerEventOperation getAllMenusPerEventOperation;
	private final GetMenuOperation getMenuOperation;

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('menu:create')")
	public ResponseEntity<CreateMenuResponse> createMenu(@RequestBody CreateMenuRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(createMenuOperation.process(request));
	}

	@GetMapping("/get")
	@PreAuthorize("hasAuthority('menu:read')")
	public ResponseEntity<GetMenuResponse> getMenu(@RequestParam("menuId") @UUID String menuId) {
		GetMenuRequest request = GetMenuRequest
				.builder()
				.menuId(menuId)
				.build();
		return ResponseEntity.ok(getMenuOperation.process(request));
	}

	@GetMapping("/get-my")
	@PreAuthorize("hasAuthority('menu:read-my-per-event')")
	public ResponseEntity<GetAllMenusPerEventResponse> getMyInvitations(@RequestParam("personalEventId") @UUID String personalEventId) {
		GetAllMenusPerEventRequest request = GetAllMenusPerEventRequest
				.builder()
				.personalEventId(personalEventId)
				.build();
		return ResponseEntity.ok(getAllMenusPerEventOperation.process(request));
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('menu:delete')")
	public ResponseEntity<DeleteMenuResponse> deleteInvitation(@RequestBody DeleteMenuRequest request) {
		return ResponseEntity.ok(deleteMenuOperation.process(request));
	}
}

