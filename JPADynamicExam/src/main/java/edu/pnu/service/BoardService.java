package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import edu.pnu.domain.dto.PageRequestDTO;
import edu.pnu.persistence.BoardRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepo;
	
	@PersistenceContext
	private EntityManager em;

	public List<Board> getBoards(Board board, PageRequestDTO pageRequestDTO) {

		StringBuilder sb = new StringBuilder("select b from Board b where b.board is null");

		String str = board.getTitle();
		if (str != null && str.length() > 0)	sb.append(" AND title like '%" + str + "%'");
		str = board.getContent();
		if (str != null && str.length() > 0)	sb.append(" AND content like '%" + str + "%'");
		str = board.getAuthor();
		if (str != null && str.length() > 0)	sb.append(" AND author like '%" + str + "%'");

		if (pageRequestDTO.getField() != null) {
			sb.append(" ORDER BY "); 
			if (pageRequestDTO.getAsc())	sb.append(pageRequestDTO.getField() + " ASC");
			else 							sb.append(pageRequestDTO.getField() + " DESC");
		}

		TypedQuery<Board> query = em.createQuery(sb.toString(), Board.class);
		
		if (0 < pageRequestDTO.getPageNumber()) {
			query.setFirstResult((pageRequestDTO.getPageNumber()-1)*pageRequestDTO.getPageSize());
			query.setMaxResults(pageRequestDTO.getPageSize());
		}
		
		return query.getResultList();
	}
	
	public Board getBoard(Board board) {
		return boardRepo.findById(board.getId()).get();
	}
	
	public Board insertBoard(Board board) {
		return boardRepo.save(board);
	}
	
	public Board updateBoad(Board board) {
		Board oldBoard = getBoard(board);
		if (oldBoard == null) return null;
		
		if (board.getTitle() != null)	oldBoard.setTitle(board.getTitle());
		if (board.getContent() != null)	oldBoard.setContent(board.getContent());
		return boardRepo.save(oldBoard);
	}
	
	public void deleteBoard(Board board) {
		boardRepo.deleteById(board.getId());
	}
}
