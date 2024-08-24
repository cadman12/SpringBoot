package edu.pnu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
class SampleDTO {
	private int id;
	private String name;
	private String content;
}

@RestController
public class TestController {

	@GetMapping("/test")
	public String test() {
		return "Test String";
	}
	
	@GetMapping("/apitest")
	public ResponseEntity<?> apitest() {
		List<SampleDTO> list = new ArrayList<>();
		for (int i = 1 ; i <= 5 ; i++) {
			list.add(SampleDTO.builder().id(i).name("name"+i).content("content"+i).build());
		}		
		return ResponseEntity.ok(list);
	}
}
