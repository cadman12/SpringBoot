package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.M2MMember;

public interface M2MMemberRepository extends JpaRepository<M2MMember, String> {

}
