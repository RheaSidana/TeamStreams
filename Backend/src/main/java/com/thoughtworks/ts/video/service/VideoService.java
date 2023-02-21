package com.thoughtworks.ts.video.service;

import com.thoughtworks.ts.channel.models.Channel;
import com.thoughtworks.ts.channel.repository.ChannelRepository;
import com.thoughtworks.ts.video.DTO.VideoUpload;
import com.thoughtworks.ts.video.models.Video;
import com.thoughtworks.ts.video.repository.VideoRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@ComponentScan("com.thoughtworks.ts.video.service.VideoRepository")
public class VideoService {
    private final VideoRepository videoRepository;
    private final ChannelRepository channelRepository;

    public VideoService(VideoRepository videoRepository,
                        ChannelRepository channelRepository) {
        this.videoRepository = videoRepository;
        this.channelRepository = channelRepository;
    }

    public List<Video> getLatestPublicVideos() {
        List<Video> latestVideos = videoRepository.findLatestPublicVideosByCreatedDate();
        return latestVideos;
    }

    public Object addVideo(VideoUpload videoUpload) throws Exception {
        Video video = new Video();
        Channel channel = channelRepository.findByName(videoUpload.getChannelName());

        if (channel == null) {
            throw new Exception("Channel Does not Exists.");
        }
        Set<Video> videoSet = channel.getVideo();
        if(videoSet!= null) {
            for (Video v : videoSet) {
                if (v.getZoom_link().trim().equals(videoUpload.getLink().trim())) {
                    throw new Exception("Video Already Exists in Channel");
                }
            }
        }
        video.setChannel(channel);
        video.setTitle(videoUpload.getTitle());
        video.setZoom_link(videoUpload.getLink());
        video.setPasscode(videoUpload.getPasscode());
        video.setDescription(videoUpload.getDescription());
        video.setPrivate_visibility(videoUpload.getVisibility());

        video = videoRepository.save(video);
        return video;
    }
}
