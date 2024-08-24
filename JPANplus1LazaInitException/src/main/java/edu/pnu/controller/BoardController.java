package edu.pnu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;
import edu.pnu.domain.dto.BoardDTO;
import edu.pnu.service.BoardService;

@RestController
public class BoardController {

	@Autowired private BoardService boardService;

	@GetMapping("/boards")
	public List<Board> boards() {
		List<Board> list = boardService.boards();
		
		List<BoardDTO> retList = new ArrayList<>();
		for (Board b : list) {
			retList.add(BoardDTO.of(b));
		}
		return list;
	}
	@GetMapping("/board")
	public BoardDTO board(Long id) {
		Board b = boardService.board(id);
		
		Member m = b.getMember();
		System.out.println(m);
		
		return BoardDTO.of(b);
	}	
}
