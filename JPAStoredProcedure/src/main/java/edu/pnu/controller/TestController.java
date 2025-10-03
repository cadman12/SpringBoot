package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.persistence.InventoryRepository;

@RestController
public class TestController {

	@Autowired
	private InventoryRepository invenRepo;	
	
	@GetMapping
	public ResponseEntity<?> test() {
		return ResponseEntity.ok(invenRepo.film_in_stock(1, 1));
	}	
}
