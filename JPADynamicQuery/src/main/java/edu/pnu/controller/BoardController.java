package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Board;
import edu.pnu.service.BoardService;

@RestController
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/")
	public ResponseEntity<?> rootTest() {
		return ResponseEntity.ok("Index");
	}
	
	@GetMapping("/board")
	public ResponseEntity<?> boardQueryBySpec(Board board) {
		return ResponseEntity.ok(boardService.boardQueryBySpec(board));
	}

	@GetMapping("/board1")
	public ResponseEntity<?> boardQueryByCriteria(Board board) {
		return ResponseEntity.ok(boardService.boardQueryByCriteria(board));
	}

	@GetMapping("/board2")
	public ResponseEntity<?> boardQueryByJPANativeQuery(Board board) {
		return ResponseEntity.ok(boardService.boardQueryByJPANativeQuery(board));
	}
	
	@GetMapping("/board3")
	public ResponseEntity<?> boardQueryByJdbcTemplate(Board board) {
		return ResponseEntity.ok(boardService.boardQueryByJdbcTemplate(board));
	}

	@GetMapping("/board4")
	public ResponseEntity<?> boardQueryByJdbcTemplate1(Board board) {
		return ResponseEntity.ok(boardService.boardQueryByJdbcTemplate1(board));
	}
	
	@GetMapping("/board5")
	public ResponseEntity<?> boardQueryByJPQL(Board board) {
		return ResponseEntity.ok(boardService.boardQueryByJPQL(board));
	}

	@GetMapping("/board6")
	public ResponseEntity<?> boardQueryByJPQLWithDTO(Board board) {
		return ResponseEntity.ok(boardService.boardQueryByJPQLWithDTO(board));
	}
}
