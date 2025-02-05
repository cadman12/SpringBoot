package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
