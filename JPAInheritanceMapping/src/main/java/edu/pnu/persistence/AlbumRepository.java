package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
