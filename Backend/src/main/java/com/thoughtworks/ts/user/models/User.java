package com.thoughtworks.ts.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.ts.channel.models.Channel;
import com.thoughtworks.ts.channel.models.MemberRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity(name = "ts_user")
@Table(name = "ts_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty
    @NotNull(message = "Email id must be provided")
    private String email;

    @Column(nullable = false)
    @JsonProperty
    @NotNull(message = "Password must be provided")
    private String password;

    @JsonManagedReference(value = "owner_reference")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Channel> channel;

    @JsonManagedReference(value = "user_reference")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<MemberRequest> memberRequests;


    @JsonIgnore
    @ManyToMany(mappedBy = "members")
    private Set<Channel> listOfChannels;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }


    public Set<Channel> getListOfChannels() {
        return listOfChannels;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Channel> getChannel() {
        return channel;
    }

    public void setChannel(Set<Channel> channel) {
        this.channel = channel;
    }

    public Set<MemberRequest> getMemberRequests() {
        return memberRequests;
    }

    public void setMemberRequests(Set<MemberRequest> memberRequests) {
        this.memberRequests = memberRequests;
    }

    public void setListOfChannels(Set<Channel> listOfChannels) {
        this.listOfChannels = listOfChannels;
    }
}