package edu.pnu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.BooleanBuilder;

import edu.pnu.domain.Board;
import edu.pnu.domain.QBoard;
import edu.pnu.persistence.BoardRepository;
import lombok.Data;

@Data
class BoardFilter {
	private String key;
	private String value;
	private Integer pageNum = 0;
	private Integer pageSize = 10;
}

@RestController
public class BoardController {

	@Autowired
	private BoardRepository boardRepo;

	@GetMapping("/board")
	public ResponseEntity<?> getBoards(BoardFilter bf) {

		List<Board> boardList = null;
		
		if (bf.getKey() == null || bf.getKey().isBlank() ||
			bf.getValue() == null || bf.getValue().isBlank()) {
			if (bf.getPageNum() <= 0) {
				boardList = boardRepo.findAll();
			}
			else {
				Pageable paging = PageRequest.of(bf.getPageNum()-1, bf.getPageSize());
				Page<Board> list = boardRepo.findAll(paging);
				boardList = list.getContent();
			}
		}
		else {
			BooleanBuilder builder = new BooleanBuilder();
			QBoard qboard = QBoard.board;
			if (bf.getKey().equalsIgnoreCase("title"))
				builder.and(qboard.title.contains(bf.getValue()));
			else
				builder.and(qboard.content.contains(bf.getValue()));

			if (bf.getPageNum() <= 0) {
				boardList = new ArrayList<>();
				Iterable<Board> iter = boardRepo.findAll(builder);
				for(Board b : iter)
					boardList.add(b);
			}
			else {
				Pageable paging = PageRequest.of(bf.getPageNum()-1, bf.getPageSize());
				Page<Board> list = boardRepo.findAll(builder, paging);
				boardList = list.getContent();
			}
		}
		return ResponseEntity.ok(boardList);
	}
}
