package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import edu.pnu.persistence.BoardRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {

	private final BoardRepository boardRepo;
	
	@PersistenceContext
	private EntityManager em;

	public List<Board> getReplys(Board board) {
		return boardRepo.findReplyByParentid(board.getId());
	}
	
	public Board getReply(Board board) {
		return boardRepo.findById(board.getId()).get();
	}
	
	public Board insertReply(Long pid, Board board) {
		Board pboard = boardRepo.findById(pid).get();
		board.setBoard(pboard);
		return boardRepo.save(board);
	}
	
	public Board updateReply(Board board) {
		Board oldBoard = getReply(board);
		if (oldBoard == null) return null;
		
		if (board.getTitle() != null)	oldBoard.setTitle(board.getTitle());
		if (board.getContent() != null)	oldBoard.setContent(board.getContent());
		return boardRepo.save(oldBoard);
	}
	
	public void deleteReply(Board board) {
		boardRepo.deleteById(board.getId());
	}
}
