package bg.tu.varna.events.rest.controller;

import bg.tu.varna.events.api.operations.user.login.AuthenticationOperation;
import bg.tu.varna.events.api.operations.user.login.AuthenticationRequest;
import bg.tu.varna.events.api.operations.user.login.AuthenticationResponse;
import bg.tu.varna.events.api.operations.user.register.business.RegisterBusinessUserOperation;
import bg.tu.varna.events.api.operations.user.register.business.RegisterBusinessUserRequest;
import bg.tu.varna.events.api.operations.user.register.business.RegisterBusinessUserResponse;
import bg.tu.varna.events.api.operations.user.register.organization.RegisterOrganizationOperation;
import bg.tu.varna.events.api.operations.user.register.organization.RegisterOrganizationRequest;
import bg.tu.varna.events.api.operations.user.register.organization.RegisterOrganizationResponse;
import bg.tu.varna.events.api.operations.user.register.personal.RegisterUserOperation;
import bg.tu.varna.events.api.operations.user.register.personal.RegisterUserRequest;
import bg.tu.varna.events.api.operations.user.register.personal.RegisterUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private final AuthenticationOperation authenticationOperation;
	private final RegisterUserOperation registerUserOperation;
	private final RegisterOrganizationOperation registerOrganizationOperation;
	private final RegisterBusinessUserOperation registerBusinessUserOperation;

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody @Valid AuthenticationRequest authenticationRequest){

		return ResponseEntity.ok(authenticationOperation.process(authenticationRequest));
	}

	@PostMapping("/register/user")
	public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody @Valid RegisterUserRequest request){
		return ResponseEntity.ok(registerUserOperation.process(request));
	}

	@PostMapping("/register/organization")
	public ResponseEntity<RegisterOrganizationResponse> registerOrganization(@RequestBody @Valid RegisterOrganizationRequest request){
		return ResponseEntity.ok(registerOrganizationOperation.process(request));
	}

	@PostMapping("/register/business_user")
	public ResponseEntity<RegisterBusinessUserResponse> registerBusinessUser(@RequestBody @Valid RegisterBusinessUserRequest request){
		return ResponseEntity.ok(registerBusinessUserOperation.process(request));
	}
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
