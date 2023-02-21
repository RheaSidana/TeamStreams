package com.thoughtworks.ts.channel.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.ts.user.models.User;
import com.thoughtworks.ts.video.models.Video;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference(value = "owner_reference")
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    @JsonManagedReference(value = "video_reference")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "channel", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Video> video;

    @JsonManagedReference(value = "channel_reference")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "channel", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<MemberRequest> memberRequests;

    @Column(nullable = false)
    @JsonProperty
    @NotNull(message = "Name must be provided")
    private String name;

    @Column(nullable = false)
    @JsonProperty
    @NotNull(message = "Description must be provided")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "member_channel_mapping",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members;

    @Column(nullable = false)
    @JsonProperty
    @NotNull(message = "City must be provided")
    private String city;


    @Column(nullable = false)
    @JsonProperty
    @NotNull(message = "Country must be provided")
    private String country;


    public Channel(User user, String name, String description, String city, String country) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.city = city;
        this.country = country;
    }

    public Channel() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonIgnore
    public Set<User> getMembers() {
        return members;
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

    public Set<Video> getVideo() {
        return video;
    }

    public void setVideo(Set<Video> video) {
        this.video = video;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MemberRequest> getMemberRequests() {
        return memberRequests;
    }

    public void setMemberRequests(Set<MemberRequest> memberRequests) {
        this.memberRequests = memberRequests;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }
}