package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
