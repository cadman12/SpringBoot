package edu.pnu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;
import edu.pnu.persistence.BoardRepository;
import edu.pnu.persistence.spec.BoardSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private EntityManager entityManager;	

    @Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<Board> boardQueryBySpec(Board board) {

		Specification<Board> spec = (root, query, criteriaBuilder) -> null;		
		
		String title = board.getTitle();
		if(title!= null && !title.isEmpty()) {
	        spec = spec.and(BoardSpecification.searchTypeTitle(title));
		}

		String content = board.getContent();
		if(content!= null && !content.isEmpty()) {
	        spec = spec.and(BoardSpecification.searchTypeContent(content));
		}
	
		return boardRepository.findAll(spec);
	}
	
	public List<Board> boardQueryByCriteria(Board board) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Board> cq = cb.createQuery(Board.class);
		Root<Board> m = cq.from(Board.class);
		
		List<Predicate> predicates = new ArrayList<>();

		String title = board.getTitle();
		if(title!= null && !title.isEmpty()) {
	        predicates.add(cb.like(m.get("title"), "%" + title + "%"));
		}

		String content = board.getContent();
		if(content!= null && !content.isEmpty()) {
	        predicates.add(cb.like(m.get("content"), "%" + content + "%"));
		}
		
		// 0개 크기를 가지는 배열을 파라미터로 넘겨주면 자동으로 predicates의 size 길이의 새로운 배열을 만들어서 될돌려 준다.
		// new Predicate[predicates.size()] 로 해도 상관없음.
		cq.where(predicates.toArray(new Predicate[0]));
		
		return entityManager.createQuery(cq).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Board> boardQueryByJPANativeQuery(Board board) {

        StringBuilder sql = new StringBuilder("SELECT * FROM board WHERE 1=1");
		String title = board.getTitle();
		String content = board.getContent();

		if(title != null && !title.isEmpty())		sql.append(" AND title like :title");
		if(content != null && !content.isEmpty())	sql.append(" AND content like :content");

        Query query = entityManager.createNativeQuery(sql.toString(), Board.class);

        if (title != null && !title.isEmpty()) {
            query.setParameter("title", "%" + title + "%");
        }
        if (content != null && !content.isEmpty()) {
            query.setParameter("content",  "%" + content + "%");
        }

        return query.getResultList();
	}
	
	public List<Board> boardQueryByJdbcTemplate(Board board) {
        StringBuilder sql = new StringBuilder("SELECT * FROM board WHERE 1=1");
		String title = board.getTitle();
		String content = board.getContent();

		if(title != null && !title.isEmpty())		sql.append(" AND title like ?");
		if(content != null && !content.isEmpty())	sql.append(" AND content like ?");

        return jdbcTemplate.query(sql.toString(), psmt -> {
        	int index = 1;
            if (title != null && !title.isEmpty()) {
            	psmt.setString(index++, "%" + title + "%");
            }
            if (content != null && !content.isEmpty()) {
            	psmt.setString(index++, "%" + content + "%");
            }
        }, (rs, rowNum) -> Board.builder()
        						.seq(rs.getLong("seq"))
        						.title(rs.getString("title"))
        						.content(rs.getString("content"))
        						.date(rs.getDate("date"))
        						.member(Member.builder().username(rs.getString("member_id")).build())
        						.build());
    }
	
	public List<Board> boardQueryByJdbcTemplate1(Board board) {
        StringBuilder sql = new StringBuilder("SELECT * FROM board "
       				+ " left join member on board.member_id = member.username "
       				+ " WHERE 1=1");
		String title = board.getTitle();
		String content = board.getContent();

		if(title != null && !title.isEmpty())		sql.append(" AND title like ?");
		if(content != null && !content.isEmpty())	sql.append(" AND content like ?");

        return jdbcTemplate.query(sql.toString(), psmt -> {
        	int index = 1;
            if (title != null && !title.isEmpty()) {
            	psmt.setString(index++, "%" + title + "%");
            }
            if (content != null && !content.isEmpty()) {
            	psmt.setString(index++, "%" + content + "%");
            }
        }, (rs, rowNum) -> Board.builder()
        						.seq(rs.getLong("seq"))
        						.title(rs.getString("title"))
        						.content(rs.getString("content"))
        						.date(rs.getDate("date"))
        						.member(Member.builder()
        										.username(rs.getString("member_id"))
        										.password(rs.getString("password"))
        										.enabled(rs.getBoolean("enabled"))
        										.role(rs.getString("role"))
        										.build())
        						.build());
    }		
}
