package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;
import edu.pnu.domain.MemberClub;

public interface MemberRepository extends JpaRepository<Member, String> {

}
