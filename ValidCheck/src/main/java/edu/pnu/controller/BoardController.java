package edu.pnu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Board;
import jakarta.validation.Valid;

@RestController
public class BoardController {

	@PostMapping("/board")
	public ResponseEntity<String> createBoard(@Valid @RequestBody Board board, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			String errMsg = bindingResult.getFieldError().getDefaultMessage();
			System.out.println(errMsg);
			return ResponseEntity.badRequest().body(errMsg);
		}
		System.out.println(board);
		return ResponseEntity.ok("ok");
    }

	// Valid Check에서 예외가 발생하면 예외발생. 따로 처리해주지 않으면 404에러 발생 
	@PostMapping("/board1")
	public ResponseEntity<String> createBoard1(@Valid @RequestBody Board board) {
		System.out.println(board);
		return ResponseEntity.ok("ok");
    }
}
