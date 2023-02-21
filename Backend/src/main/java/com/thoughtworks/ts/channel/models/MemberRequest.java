package com.thoughtworks.ts.channel.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.thoughtworks.ts.user.models.User;

import javax.persistence.*;

@Entity(name = "member_request")
public class MemberRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference("user_reference")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference("channel_reference")
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    public MemberRequest() {
    }

    public MemberRequest(User user, Channel channel) {
        this.user = user;
        this.channel = channel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
