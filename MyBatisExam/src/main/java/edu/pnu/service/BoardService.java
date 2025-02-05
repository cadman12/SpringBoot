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
	
	public Board getBoard(Long id) {
		return boardMapper.getBoard(id);
	}
	
	public List<Board> getBoards(Board board) {
		return boardMapper.getBoards(board);
	}
	
	public Board insertBoard(Board board) {
		boardMapper.insertBoard(board);
		return board;
	}
	
	public Board updateBoard(Board board) {
		boardMapper.updateBoard(board);
		return boardMapper.getBoard(board.getId());
	}
	
	public void deleteBoard(Board board) {
		boardMapper.deleteBoard(board.getId());
	}
}
