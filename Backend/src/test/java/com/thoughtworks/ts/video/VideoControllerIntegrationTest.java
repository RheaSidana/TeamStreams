package com.thoughtworks.ts.video;

import com.thoughtworks.ts.TsApplication;
import com.thoughtworks.ts.channel.models.Channel;
import com.thoughtworks.ts.channel.repository.ChannelRepository;
import com.thoughtworks.ts.channel.repository.MemberRequestRepository;
import com.thoughtworks.ts.user.models.User;
import com.thoughtworks.ts.user.repository.UserRepository;
import com.thoughtworks.ts.video.repository.VideoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TsApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class VideoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRequestRepository memberRequestRepository;


    @BeforeEach
    void before() {
        userRepository.deleteAll();
        videoRepository.deleteAll();
        channelRepository.deleteAll();
        memberRequestRepository.deleteAll();
    }

    @AfterEach
    void after() {
        userRepository.deleteAll();
        videoRepository.deleteAll();
        channelRepository.deleteAll();
        memberRequestRepository.deleteAll();
    }

    @Test
    void shouldUploadVideoSuccessfully() throws Exception {
        User owner = new User("owner@gmail.com", "password");
        owner = userRepository.save(owner);
        Channel channel = new Channel(owner, "channel1", "description1", "gurgaon", "india");
        channel = channelRepository.save(channel);

        String requestJson = "{\"channelName\":" + "\"channel1\""
                + ",\"title\":" + "\"link\""
                + ",\"link\":\"abc\""
                + ",\"passcode\":\"passcode\""
                + ",\"description\":\"test-description\""
                + ",\"visibility\":" + true + "}";

        mockMvc.perform(post("/video/upload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(videoRepository.findAllVideosByChannel_Id(channel.getId()).size(), 1);
    }
    @Test
    void shouldThrowExceptionWhenChannelDoesNotExists() throws Exception {

        String requestJson = "{\"channelName\":" + "\"channel1\""
                + ",\"title\":" + "\"link\""
                + ",\"link\":\"abc\""
                + ",\"passcode\":\"passcode\""
                + ",\"description\":\"test-description\""
                + ",\"visibility\":" + true + "}";

        mockMvc.perform(post("/video/upload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(videoRepository.findAll().size(),0);
    }
}
