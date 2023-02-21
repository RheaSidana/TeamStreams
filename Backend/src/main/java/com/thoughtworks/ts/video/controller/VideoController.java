package com.thoughtworks.ts.video.controller;

import com.thoughtworks.ts.video.DTO.VideoUpload;
import com.thoughtworks.ts.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("video")
public class VideoController {
    private VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("latest-videos/")
    public ResponseEntity<Object> getLatestVideos() {
        return new ResponseEntity<Object>(videoService.getLatestPublicVideos(), HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE)
    @NotNull
    @NotBlank
    public ResponseEntity<Object> uploadVideo(@RequestBody VideoUpload videoUpload) throws Exception {
        try {
            return new ResponseEntity<>(videoService.addVideo(videoUpload), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}



