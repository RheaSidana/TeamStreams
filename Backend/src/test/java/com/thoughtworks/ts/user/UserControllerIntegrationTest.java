package com.thoughtworks.ts.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.ts.TsApplication;
import com.thoughtworks.ts.channel.repository.ChannelRepository;
import com.thoughtworks.ts.user.repository.UserRepository;
import com.thoughtworks.ts.video.repository.VideoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = TsApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @BeforeEach
    void before() {

        userRepository.deleteAll();
        videoRepository.deleteAll();
        channelRepository.deleteAll();


    }

    @AfterEach
    void after() {

        userRepository.deleteAll();
        videoRepository.deleteAll();
        channelRepository.deleteAll();


    }

    //working on member request id 3
//    @Test
//    void should_Return_Member_Request_After_Sending_Request_To_Join_A_Channel_Successfully() throws Exception {
//        User owner = new User("owner@gmail.com", "password");
//        Channel channel = new Channel(owner, "channel1", "description1", "gurgaon", "india");
//        User member = new User("member@gmail.com", "password");
//        member.setId(1L);
//        channel.setId(1L);
//        userRepository.save(owner);
//        member = userRepository.save(member);
//        channel = channelRepository.save(channel);
//        mockMvc.perform(post("/user/sendRequest?user_id="+member.getId()+"&channel_id="+channel.getId())).andExpect(status().isAccepted()).andExpect(
//                content().json(
//                        "{" +
//                                "    \"id\": 1" +
//                                "}"
//                )
//        );
//    }
}
