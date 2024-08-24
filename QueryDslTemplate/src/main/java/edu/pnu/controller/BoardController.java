package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Board;
import edu.pnu.domain.query.SearchConditionForBoard;
import edu.pnu.service.executor.BoardQueryExecutorByBuilder;

@RestController
public class BoardController {

	@Autowired
	private BoardQueryExecutorByBuilder boardQueryExecutorByBuilder;
	
	@GetMapping("/board")
	public ResponseEntity<?> getBoardAll1(SearchConditionForBoard searchCond) {
		List<Board> list = boardQueryExecutorByBuilder.query(searchCond);
		System.out.println("size:" + list.size());
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping("/board")
	public ResponseEntity<?> getBoardAll2(@RequestBody SearchConditionForBoard searchCond) {
		List<Board> list = boardQueryExecutorByBuilder.query(searchCond);
		System.out.println("size:" + list.size());
		return ResponseEntity.ok().body(list);
	}
}
