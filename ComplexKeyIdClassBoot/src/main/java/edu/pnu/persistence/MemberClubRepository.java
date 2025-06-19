package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.MemberClub;
import edu.pnu.domain.MemberClubId;

public interface MemberClubRepository extends JpaRepository<MemberClub, MemberClubId> {
	List<MemberClub> findByClub_ClubName(String name);
	List<MemberClub> findByMember_Username(String name);
	MemberClub findByMember_UsernameAndClub_ClubName(String username, String clubname);	
}
