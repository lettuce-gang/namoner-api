package com.toy.namoner.monitor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitorController {

	/**
	 * 모니터링용 엔드포인트
	 */
	@GetMapping("/monitor")
	public void monitor() {
	}
}
