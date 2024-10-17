package edu.pnu.persistence.spec;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import edu.pnu.domain.Board;

public class BoardSpecification {

	// 글 제목에 따른 검색
	public static Specification<Board> searchTypeTitle(String searchKeyword) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + searchKeyword + "%");
	}

	// 작성자에 따른 검색
	public static Specification<Board> searchTypeWriter(String searchKeyword) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("member").get("username"), searchKeyword);
	}

	// 글 내용에 따른 검색
	public static Specification<Board> searchTypeContent(String searchKeyword) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("content"), "%" + searchKeyword + "%");
	}

	// 작성일에 따른 구분
	public static Specification<Board> dateGreaterThan(Date date) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("date"), date);
	}

	public static Specification<Board> dateEqual(Date date) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), date);
	}
	
	public static Specification<Board> dateLessThan(Date date) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("date"), date);
	}
}
