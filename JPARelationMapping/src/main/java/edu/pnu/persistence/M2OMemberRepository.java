package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.M2OMember;

public interface M2OMemberRepository extends JpaRepository<M2OMember, String> {

}
