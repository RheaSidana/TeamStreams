package com.thoughtworks.ts.video.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.ts.channel.models.Channel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty
    @NotNull(message = "Title must be provided")
    private String title;

    @Column(nullable = false)
    @JsonProperty
    @NotNull(message = "Link must be provided")
    private String zoom_link;

    @Column(nullable = false)
    @JsonProperty
    @NotNull(message = "Passcode must be provided")
    private String passcode;

    @Column(nullable = false)
    @JsonProperty
    @NotNull(message = "Description must be provided")
    private String description;

    @Column(nullable = false)
    @JsonProperty
    private Boolean private_visibility;


    @JsonBackReference(value = "video_reference")
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    public Video() {
    }

    public Video(String title, String zoom_link, String passcode, String description, Boolean private_visibility, Channel channel) {
        this.title = title;
        this.zoom_link = zoom_link;
        this.passcode = passcode;
        this.description = description;
        this.private_visibility = private_visibility;
        this.channel = channel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZoom_link() {
        return zoom_link;
    }

    public void setZoom_link(String zoom_link) {
        this.zoom_link = zoom_link;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPrivate_visibility() {
        return private_visibility;
    }

    public void setPrivate_visibility(Boolean private_visibility) {
        this.private_visibility = private_visibility;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
