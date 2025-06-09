package com.project.skill_share;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class Main {
	
	@GetMapping("/test/hello")
	public String test() {
		return "you have protected resource";
	}
}
