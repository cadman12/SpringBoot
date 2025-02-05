package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class BoardService {

	@PersistenceContext
	private EntityManager em;

	public List<Board> getBoards(Board board, Integer pageNumber, Integer pageSize, Boolean asc, String field) {

		StringBuilder sb = new StringBuilder("select b from Board b where 1=1");

		String str = board.getTitle();
		if (str != null && str.length() > 0)
			sb.append(" AND title like '%" + str + "%'");
		str = board.getContent();
		if (str != null && str.length() > 0)
			sb.append(" AND content like '%" + str + "%'");
		str = board.getAuthor();
		if (str != null && str.length() > 0)
			sb.append(" AND author like '%" + str + "%'");

		sb.append(" ORDER BY "); 
		if (asc)	sb.append(field + " ASC");
		else 		sb.append(field + " DESC");

		TypedQuery<Board> query = em.createQuery(sb.toString(), Board.class);
		query.setFirstResult((pageNumber-1)*pageSize);
		query.setMaxResults(pageSize);
		
		return query.getResultList();
	}
}
