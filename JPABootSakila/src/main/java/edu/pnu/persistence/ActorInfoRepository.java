package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ActorInfo;

public interface ActorInfoRepository extends JpaRepository<ActorInfo, Integer> {

	List<ActorInfo> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
