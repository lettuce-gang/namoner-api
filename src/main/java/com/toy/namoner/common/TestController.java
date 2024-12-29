package com.toy.namoner.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RestController
public class TestController {
	@GetMapping("/success")
	public TestModel success() {
		return new TestModel("test", 1);
	}

	@GetMapping("/error")
	public void error() {
		throw new RuntimeException("error");
	}

	@Getter
	@RequiredArgsConstructor
	public static class TestModel {
		private final String name;
		private final int age;
	}
}
