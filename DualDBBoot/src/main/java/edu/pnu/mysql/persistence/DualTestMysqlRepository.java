package edu.pnu.mysql.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.mysql.entity.DualTestMySQL;

public interface DualTestMysqlRepository extends JpaRepository<DualTestMySQL, Long> {

}
