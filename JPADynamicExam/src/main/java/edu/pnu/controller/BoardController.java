package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
    	
    	Integer pageNumber = pageRequestDTO.getPageNumber();
    	if (pageNumber == null) pageNumber = 1;
    	Integer pageSize = pageRequestDTO.getPageSize();
    	if (pageSize == null) pageSize = 5;
    	Boolean asc = pageRequestDTO.getAsc();
    	if (asc == null)	asc = true;
    	String field = pageRequestDTO.getField();
    	if (field == null)	field = "id";
    	
        return boardService.getBoards(board, pageNumber, pageSize, asc, field);
    }
    
}
