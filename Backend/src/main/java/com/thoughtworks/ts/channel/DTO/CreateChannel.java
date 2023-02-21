package com.thoughtworks.ts.channel.DTO;

import com.thoughtworks.ts.user.models.User;

import java.util.Set;

public class CreateChannel {
    private String name;
    private long owner_id;
    private String description;
    private String city;
    private String country;
    private Set<User> members;


    public CreateChannel(String name, long owner_id, String description, String city, String country, Set<User> members) {
        this.name = name;
        this.owner_id = owner_id;
        this.description = description;
        this.city = city;
        this.country = country;
        this.members = members;
    }
    public CreateChannel(){}

    public String getCity() {
        return city;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(long owner_id) {
        this.owner_id = owner_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
