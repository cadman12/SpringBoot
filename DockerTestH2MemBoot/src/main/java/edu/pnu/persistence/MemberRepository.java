package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
