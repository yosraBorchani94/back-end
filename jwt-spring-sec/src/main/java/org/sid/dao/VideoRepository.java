package org.sid.dao;


import org.sid.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository  extends JpaRepository<Video, Long>, CrudRepository<Video, Long>{

}
