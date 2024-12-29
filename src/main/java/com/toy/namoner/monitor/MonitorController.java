package com.toy.namoner.monitor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitorController {

	@GetMapping("/monitor")
	public void monitor() {
	}
}
