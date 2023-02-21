package com.thoughtworks.ts.channel;

import com.thoughtworks.ts.TsApplication;
import com.thoughtworks.ts.channel.models.Channel;
import com.thoughtworks.ts.channel.models.MemberRequest;
import com.thoughtworks.ts.channel.repository.ChannelRepository;
import com.thoughtworks.ts.channel.repository.MemberRequestRepository;
import com.thoughtworks.ts.user.models.User;
import com.thoughtworks.ts.user.repository.UserRepository;
import com.thoughtworks.ts.video.models.Video;
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

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TsApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ChannelControllerIntegrationTest {

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
    void should_Return_List_Of_Members_Successfully() throws Exception {
        User owner = new User("owner@gmail.com", "password");
        User member1 = new User("member1@gmail.com", "member1");
        User member2 = new User("member2@gmail.com", "member2");
        Channel channel = new Channel(owner, "chanel1", "description1", "gurgaon", "india");
        channel.setId(1L);

        Set<User> members = new HashSet<>();
        members.add(member1);
        members.add(member2);
        channel.setMembers(members);
        userRepository.save(owner);
        member1 = userRepository.save(member1);
        member2 = userRepository.save(member2);
        channel = channelRepository.save(channel);

        mockMvc.perform(get("/channel/" + channel.getId() + "/view-members")).andExpect(status().is2xxSuccessful()).andExpect(
                content().json(
                        "[{'id':" + member2.getId() + ",'email':'member2@gmail.com','password':'member2','channel':[],'memberRequests':[]},{'id': " + member1.getId() + ",'email':'member1@gmail.com','password':'member1','channel':[],'memberRequests':[]}]"
                )
        ).andReturn();
    }

    @Test
    void should_Return_Channel_Information_Successfully() throws Exception {
        User owner = new User("owner@gmail.com", "password");
        Channel channel = new Channel(owner, "channel1", "description1", "gurgaon", "india");
        channel.setId(1L);
        Set<Channel> channels = new HashSet<>();

        channels.add(channel);
        owner.setChannel(channels);
        userRepository.save(owner);
        channel = channelRepository.save(channel);

        mockMvc.perform(get("https://localhost:8000/channel/" + channel.getId())).andExpect(status().is2xxSuccessful()).andExpect(
                content().json(
                        "{ id: " + channel.getId() + ",video: [ ]," +
                                "memberRequests: [ ]," +
                                "name: 'channel1', 'description': 'description1'}"
                )
        );
    }


    @Test
    void should_Return_Set_Of_videos_For_A_Channel_Successfully() throws Exception {
        User owner = new User("owner@gmail.com", "password");
        Channel channel = new Channel(owner, "channel1", "description1", "gurgaon", "india");
        Video video1 = new Video("Video1", "link1", "passcode1", "Description1", false, channel);
        Video video2 = new Video("Video2", "link2", "passcode2", "Description2", false, channel);

        userRepository.save(owner);
        channel = channelRepository.save(channel);

        Set<Channel> channels = new HashSet<>();
        channels.add(channel);
        video1 = videoRepository.save(video1);
        video2 = videoRepository.save(video2);

        mockMvc.perform(get("/channel/" + channel.getId() + "/videos")).andExpect(status().is2xxSuccessful()).andExpect(
                content().json(
                        "[" +
                                "{" +
                                "\"id\": " + video1.getId() + "," +
                                "    \"title\": \"Video1\"," +
                                "    \"zoom_link\": \"link1\"," +
                                "    \"passcode\": \"passcode1\"," +
                                "    \"description\": \"Description1\"," +
                                "    \"private_visibility\": false" +
                                "}," +
                                "{" +
                                "\"id\": " + video2.getId() + "," +
                                "    \"title\": \"Video2\"," +
                                "    \"zoom_link\": \"link2\"," +
                                "    \"passcode\": \"passcode2\"," +
                                "    \"description\": \"Description2\"," +
                                "    \"private_visibility\": false" +
                                "}" +
                                "]"
                )
        ).andReturn();
    }

    @Test
    void should_Return_Set_Of_Pending_Member_Request_For_A_Channel_Successfully() throws Exception {
        User owner = new User("owner@gmail.com", "password");
        Channel channel = new Channel(owner, "channel1", "description1", "gurgaon", "india");
        MemberRequest memberRequest1 = new MemberRequest();
        MemberRequest memberRequest2 = new MemberRequest();

        channel.setId(1L);
        userRepository.save(owner);
        channel = channelRepository.save(channel);
        memberRequest1.setChannel(channel);
        memberRequest2.setChannel(channel);
        memberRequest1 = memberRequestRepository.save(memberRequest1);
        memberRequest2 = memberRequestRepository.save(memberRequest2);

        mockMvc.perform(get("/channel/" + channel.getId() + "/request")).andExpect(status().is2xxSuccessful()).andExpect(
                content().json(
                        "[" +
                                "{" +
                                "\"id\": " + memberRequest1.getId() +
                                "}," +
                                "{" +
                                "\"id\": " + memberRequest2.getId() +
                                "}" +
                                "]"
                )
        ).andReturn();
    }

    @Test
    void should_Return_User_After_Accepting_Member_Request_For_A_Channel_Successfully() throws Exception {
        User owner = new User("owner@gmail.com", "password");
        Channel channel = new Channel(owner, "channel1", "description1", "gurgaon", "india");
        User member = new User("member@gmail.com", "password");
        MemberRequest memberRequest = new MemberRequest(member, channel);

        memberRequest.setId(1L);
        userRepository.save(owner);
        channel = channelRepository.save(channel);
        member = userRepository.save(member);
        memberRequest = memberRequestRepository.save(memberRequest);

        mockMvc.perform(put("/channel/" + memberRequest.getId() + "/request/accept")).andExpect(status().is2xxSuccessful()).andExpect(
                content().json(
                        "{" +
                                "    \"id\": " + member.getId() + "," +
                                "    \"email\": \"member@gmail.com\"," +
                                "    \"password\": \"password\"," +
                                "    \"channel\": [" +
                                "    ]," +
                                "    \"memberRequests\": [ " +
                                "{" +
                                "id: " + memberRequest.getId() +
                                "}" +
                                "    ]\n" +
                                "}"
                )
        ).andReturn();
    }


    @Test
    void should_Return_String_After_Rejecting_Member_Request_For_A_Channel_Successfully() throws Exception {
        User owner = new User("owner@gmail.com", "password");
        Channel channel = new Channel(owner, "channel1", "description1", "gurgaon", "india");
        User member = new User("member@gmail.com", "password");
        MemberRequest memberRequest = new MemberRequest(member, channel);

        memberRequest.setId(1L);
        userRepository.save(owner);
        channelRepository.save(channel);
        userRepository.save(member);
        memberRequest = memberRequestRepository.save(memberRequest);

        mockMvc.perform(put("/channel/" + memberRequest.getId() + "/request/reject")).andExpect(status().is2xxSuccessful()).andExpect(
                content().string("Member Request rejected successfully")
        ).andReturn();

    }

    @Test
    void shouldThrowExceptionWhenUserNotExists() throws Exception {
        User user = new User("test@test.com", "pass");
        user.setId(1L);
        userRepository.save(user);

        String requestJson = "{\"name\":" + "\"channel1\""
                + ",\"owner_id\":\"3\""
                + ",\"description\":\"desc\"" + "}";

        mockMvc.perform(post("/channel/createChannel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound()).andExpect(content().string("User Not Found"));
    }

    @Test
    void shouldCreateNewChannel() throws Exception {
        User user = new User("test@test.com", "pass");
        user = userRepository.save(user);
        Set<User> userSet = new HashSet<>();
        String requestJson = "{\"name\":" + "\"channel1\""
                + ",\"owner_id\":" + user.getId()
                + ",\"description\":\"desc\""
                + ",\"city\":\"gurgaon\""
                + ",\"country\":\"india\""
                + ",\"members\":" + userSet + "}";

//        String responseJson = "{\"id\": 1," +
//                "\"video\":\"null\"," +
//                "\"memberRequests\":\"null\"," +
//                "\"name\":\"channel1\"," +
//                "\"city\":\"gurgaon\"," +
//                "\"country\":\"india\"," +
//                "\"description\":\"desc\"}";

        mockMvc.perform(post("/channel/createChannel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(channelRepository.findAll().size(), 1);
//        assertEquals(channelRepository.findById(Mockito.any()).get().getUser().getId(), user.getId());
    }

}

