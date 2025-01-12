package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.O2OMember;

public interface O2OMemberRepository extends JpaRepository<O2OMember, String> {

}
