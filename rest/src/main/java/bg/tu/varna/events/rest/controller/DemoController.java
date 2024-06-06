package bg.tu.varna.events.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authorizations")
public class DemoController {
	@GetMapping("/admin")
	public ResponseEntity<String> testAdmin(){
		return ResponseEntity.ok("Admin::");
	}
	@GetMapping("/business_admin")
	public ResponseEntity<String> testBusinessAdmin(){
		return ResponseEntity.ok("BusinessAdmin::");
	}
	@GetMapping("/business_user")
	public ResponseEntity<String> testBusinessUser(){
		return ResponseEntity.ok("BusinessUser::");
	}
	@GetMapping("/user")
	public ResponseEntity<String> testUser(){
		return ResponseEntity.ok("User::");
	}


}
