package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Board;
import edu.pnu.domain.dto.PageRequestDTO;
import edu.pnu.service.BoardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board")
    public List<Board> getBoards(Board board, PageRequestDTO pageRequestDTO) {
        return boardService.getBoards(board, pageRequestDTO);
    }
    
    @PostMapping("/board")
    public Board insertBoard(Board board) {
    	return boardService.insertBoard(board);
    }
    
    @PutMapping("/board")
    public Board updateBoard(Board board) {
    	return boardService.updateBoad(board);
    }
    
    @DeleteMapping("/board")
    public void deleteBoard(Board board) {
    	boardService.deleteBoard(board);
    }
}
