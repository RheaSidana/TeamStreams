package com.thoughtworks.ts.video.repository;

import com.thoughtworks.ts.video.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("select v FROM video v where v.channel.id=:id")
    public List<Video> findAllVideosByChannel_Id(@Param("id") long id);

    @Query(value = "SELECT * FROM video WHERE private_visibility = false ORDER BY created_date DESC LIMIT 10", nativeQuery = true)
    List<Video> findLatestPublicVideosByCreatedDate();


}
