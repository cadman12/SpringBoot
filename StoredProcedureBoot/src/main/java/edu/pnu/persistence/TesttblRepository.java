package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import edu.pnu.domain.Testtbl;

public interface TesttblRepository extends JpaRepository<Testtbl, Long> {

	@Procedure(name = "searchschedule")
	List<Testtbl> searchSchedule(int yy, int mm, int dd);
}
