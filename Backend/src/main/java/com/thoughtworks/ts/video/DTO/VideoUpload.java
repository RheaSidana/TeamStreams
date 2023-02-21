package com.thoughtworks.ts.video.DTO;

public class VideoUpload {
    private String channelName;

    private String title;
    private String link;
    private String passcode;
    private String description;
    private Boolean visibility;

    public VideoUpload(String channelName, String title, String link, String passcode, String description, boolean visibility) {
        this.channelName = channelName;
        this.title = title;
        this.link = link;
        this.passcode = passcode;
        this.description = description;
        this.visibility = visibility;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }


}
