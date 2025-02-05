package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import edu.pnu.persistence.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	public List<Board> getBoards(Board board) {
		return boardMapper.getBoards(board.getTitle(),
				board.getContent(), board.getAuthor());
	}
}
