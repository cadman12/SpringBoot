package edu.pnu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

	@Query("select m from Member m join fetch m.boardList")
	List<Member> findMemberByFJ();

	@EntityGraph(value = "Member.boardList", type = EntityGraphType.LOAD)
	@Query("select m from Member m")
	List<Member> findMemberByEG();
}
