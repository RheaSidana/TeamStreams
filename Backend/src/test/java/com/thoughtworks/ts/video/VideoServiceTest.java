package com.thoughtworks.ts.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.ts.channel.models.Channel;
import com.thoughtworks.ts.channel.repository.ChannelRepository;
import com.thoughtworks.ts.user.models.User;
import com.thoughtworks.ts.video.DTO.VideoUpload;
import com.thoughtworks.ts.video.models.Video;
import com.thoughtworks.ts.video.repository.VideoRepository;
import com.thoughtworks.ts.video.service.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VideoServiceTest {

    private VideoService videoService;
    private Video video1;
    private Video video2;
    private Video video3;
    private Video video4;
    private Channel channel;
    private User user;


    @Autowired
    VideoRepository mockVideoRepository;

    @Autowired
    private ChannelRepository mockChannelRepository;


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        mockVideoRepository = mock(VideoRepository.class);
        mockChannelRepository = mock(ChannelRepository.class);
        videoService = new VideoService(mockVideoRepository, mockChannelRepository);
        user = new User("test-user-1@test.com", "pass");
        channel = new Channel(user, "channel1", "desc", "gurgaon", "india");
        user.setId(1L);
        channel.setId(1L);
        video1 = new Video("Java Lecture-1", "abc", "1234", "java collections", true, channel);
        video2 = new Video("Java Lecture-2", "abcd", "12345", "java collections-2", true, channel);
        video3 = new Video("Python Lecture-1", "abcf", "123456", "python basics", false, channel);
        video4 = new Video("Python Lecture-2", "abcde", "1234567", "python advanced", true, channel);
    }

    @Test
    void shouldReturnLatestPublicVideos() {
        when(mockVideoRepository.findLatestPublicVideosByCreatedDate()).thenReturn(List.of(video3));

        List<Video> expectedVideoList = videoService.getLatestPublicVideos();

        assertNotNull(expectedVideoList);
        assertThat(1, is(expectedVideoList.size()));
        assertEquals(expectedVideoList, mockVideoRepository.findLatestPublicVideosByCreatedDate());
    }

    @Test
    void shouldReturnTop10LatestPublicVideosWhenMoreThanTenVideoArePresent() {
        Video video5 = new Video("Java Lecture-1", "abc", "1234", "java collections", false, channel);
        Video video6 = new Video("Java Lecture-2", "abcd", "12345", "java collections-2", false, channel);
        Video video7 = new Video("Python Lecture-1", "abcf", "123456", "python basics", false, channel);
        Video video8 = new Video("Python Lecture-2", "abcde", "1234567", "python advanced", false, channel);
        Video video9 = new Video("Java Lecture-1", "abc", "1234", "java collections", false, channel);
        Video video10 = new Video("Java Lecture-2", "abcd", "12345", "java collections-2", false, channel);
        Video video11 = new Video("Python Lecture-1", "abcf", "123456", "python basics", false, channel);
        Video video12 = new Video("Python Lecture-2", "abcde", "1234567", "python advanced", false, channel);
        Video video13 = new Video("Java Lecture-2", "abcd", "12345", "java collections-2", false, channel);

        when(mockVideoRepository.saveAll(List.of(video3, video5, video6, video7, video8, video9, video10, video11, video12, video13))).thenReturn(List.of(video3, video5, video6, video7, video8, video9, video10, video11, video12, video13));
        when(mockVideoRepository.findLatestPublicVideosByCreatedDate()).thenReturn(List.of(video3, video5, video6, video7, video8, video9, video10, video11, video12, video13));

        List<Video> expectedVideoList = videoService.getLatestPublicVideos();

        assertNotNull(expectedVideoList);
        assertEquals(expectedVideoList, mockVideoRepository.findLatestPublicVideosByCreatedDate());
        assertThat(10, is(expectedVideoList.size()));
    }

    @Test
    void shouldReturnVideoWhenVideoIsSuccessfullyUploaded() throws Exception {
        VideoUpload videoUpload = new VideoUpload(channel.getName(), "Test-Video-1", "Link", "passcode", "test-Description", false);
        Video video = new Video(videoUpload.getTitle(), videoUpload.getLink(), videoUpload.getPasscode(), videoUpload.getDescription(), videoUpload.getVisibility(), channel);

        when(mockChannelRepository.findByName(videoUpload.getChannelName())).thenReturn(channel);
        when(mockVideoRepository.save(Mockito.any(Video.class))).thenReturn(video);

        videoService.addVideo(videoUpload);

        verify(mockChannelRepository, times(1)).findByName(videoUpload.getChannelName());
        verify(mockVideoRepository, times(1)).save(any(Video.class));
    }

    @Test
    void shouldThrowExceptionWhenChannelDoesNotExists() {
        VideoUpload videoUpload = new VideoUpload("hellooooo", "Test-Video-1", "Link", "passcode", "test-Description", false);

        when(mockChannelRepository.findByName(videoUpload.getChannelName())).thenReturn(null);

        assertThrows(Exception.class, () -> videoService.addVideo(videoUpload));
        verify(mockChannelRepository, times(1)).findByName(videoUpload.getChannelName());
    }
    @Test
    void shouldThrowExceptionWhenVideoAlreadyExistsForAChannel() {
        Video video =new Video("Test-Video-1",  "Link", "passcode", "test-Description",true, channel);
        Set<Video> videos = new HashSet<>();
        videos.add(video);
        channel.setVideo(videos);
        VideoUpload videoUpload = new VideoUpload("channel1", "Test-Video-1", "Link", "passcode", "test-Description", false);

        when(mockChannelRepository.findByName(videoUpload.getChannelName())).thenReturn(channel);

        assertThrows(Exception.class, () -> videoService.addVideo(videoUpload));
        verify(mockChannelRepository, times(1)).findByName(videoUpload.getChannelName());
    }

}