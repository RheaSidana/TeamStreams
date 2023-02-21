package com.thoughtworks.ts.channel.controller;

import com.sun.jdi.InternalException;
import com.thoughtworks.ts.channel.DTO.CreateChannel;
import com.thoughtworks.ts.channel.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("channel")
public class ChannelController {

    private ChannelService channelService;

    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping("/{id}/view-members")
    public ResponseEntity<Object> getMemberList(@PathVariable long id) {
        try {
            return new ResponseEntity<Object>(channelService.getMemberList(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getInformation(@PathVariable long id) {
        try {
            return new ResponseEntity<Object>(channelService.getInformation(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}/videos")
    public ResponseEntity<Object> getVideos(@PathVariable long id) {
        try {
            return new ResponseEntity<Object>(channelService.getVideos(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/request")
    public ResponseEntity<Object> getPendingRequests(@PathVariable long id) throws Exception {
        try {
            return new ResponseEntity<Object>(channelService.getPendingRequests(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("owner/{owner_id}")
    public ResponseEntity<Object> getChannelsOwnedByUser(@PathVariable long owner_id) throws Exception {
        try {
            return new ResponseEntity(channelService.getChannelsOwned(owner_id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/createChannel", consumes = MediaType.APPLICATION_JSON_VALUE)
    @NotNull
    @NotBlank
    public ResponseEntity<Object> createChannel(@RequestBody CreateChannel channel) throws Exception {
        if (channel.getName().isBlank()) {
            return new ResponseEntity<>("Channel name is required", HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<>(channelService.addChannel(channel), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{member_request_id}/request/accept")
    public ResponseEntity<Object> acceptMemberRequest(@PathVariable long member_request_id) {
        try {
            return new ResponseEntity<>(channelService.acceptMemberRequest(member_request_id), HttpStatus.OK);
        } catch (InternalException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{member_request_id}/request/reject")
    public ResponseEntity<Object> rejectMemberRequest(@PathVariable long member_request_id) {
        try {
            return new ResponseEntity<>(channelService.rejectMemberRequest(member_request_id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
