package com.stream.app.repositories;

import com.stream.app.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video,String>
{
    Optional<Video> findByTitle(String title);

    //query methods

    //native

    //criteria api

}
