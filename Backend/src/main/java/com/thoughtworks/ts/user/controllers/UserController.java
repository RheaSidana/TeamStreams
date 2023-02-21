package com.thoughtworks.ts.user.controllers;

import com.thoughtworks.ts.exceptions.InvalidRequestException;
import com.thoughtworks.ts.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sendRequest")
    public ResponseEntity<Object> sendRequest(
            @RequestParam("user_id") Long user_id,
            @RequestParam("channel_id") Long channel_id) {
        try {
            return new ResponseEntity<Object>(userService.addRequest(user_id, channel_id), HttpStatus.ACCEPTED);
        } catch (InvalidRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("userDetails/{userId}")
    public ResponseEntity<Object> userDetails(@PathVariable Long userId) throws Exception {

        try {
            return new ResponseEntity<Object>(userService.getUserDetails(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("channels/{userId}")
    public ResponseEntity<Object> user(@PathVariable Long userId) throws Exception {

        try {
            return new ResponseEntity<Object>(userService.getChannelsJoinedAsMembers(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("all")
    public ResponseEntity<Object> userList() throws Exception {
        return new ResponseEntity<Object>(userService.getAllUsers(), HttpStatus.OK);
    }
}
