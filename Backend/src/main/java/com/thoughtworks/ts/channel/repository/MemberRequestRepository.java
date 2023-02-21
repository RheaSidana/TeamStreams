package com.thoughtworks.ts.channel.repository;

import com.thoughtworks.ts.channel.models.MemberRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRequestRepository extends JpaRepository<MemberRequest, Long> {
    @Query("Select m from member_request m where m.channel.id=:id")
    public List<MemberRequest> findMemberRequestsByChannelId(@Param("id") long id);

    @Query("Select m from member_request m where (m.user.id=:u_id and m.channel.id=:c_id)")
    public MemberRequest existsMemberRequestWithChannelIdAndUserId(@Param("u_id") long user_id, @Param("c_id") long channel_id);
}

