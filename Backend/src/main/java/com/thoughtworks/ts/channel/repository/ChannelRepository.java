package com.thoughtworks.ts.channel.repository;

import com.thoughtworks.ts.channel.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    @Query(value = "SELECT * FROM CHANNEL WHERE owner_id= ?1 ", nativeQuery = true)
    List<Channel> findByOwnerId(long owner_id);

    Channel findByName(String channelName);
}
