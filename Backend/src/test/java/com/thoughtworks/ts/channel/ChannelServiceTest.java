package com.thoughtworks.ts.channel;

import com.sun.jdi.InternalException;
import com.thoughtworks.ts.channel.DTO.CreateChannel;
import com.thoughtworks.ts.channel.models.Channel;
import com.thoughtworks.ts.channel.models.MemberRequest;
import com.thoughtworks.ts.channel.repository.ChannelRepository;
import com.thoughtworks.ts.channel.repository.MemberRequestRepository;
import com.thoughtworks.ts.channel.service.ChannelService;
import com.thoughtworks.ts.user.models.User;
import com.thoughtworks.ts.user.repository.UserRepository;
import com.thoughtworks.ts.video.models.Video;
import com.thoughtworks.ts.video.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ChannelServiceTest {

    private ChannelRepository mockChannelRepository;
    private VideoRepository mockVideoRepository;
    private MemberRequestRepository mockMemberRequestRepository;
    private UserRepository mockUserRepository;
    private ChannelService channelService;
    private User user;
    private User user1;
    private Channel channel;

    private Video video;

    private MemberRequest memberRequest;


    @BeforeEach
    public void beforeEach() {
        mockChannelRepository = mock(ChannelRepository.class);
        mockVideoRepository = mock(VideoRepository.class);
        mockMemberRequestRepository = mock(MemberRequestRepository.class);
        mockUserRepository = mock(UserRepository.class);

        user = new User("test-user-1@test.com", "pass");
        channel = new Channel(user, "channel1", "desc", "gurgaon", "india");
        user1 = new User("test-user-2@test.com", "password");
        memberRequest = new MemberRequest(user1, channel);
        video = new Video("test_title1", "link1", "passcode1", "test_description", false, channel);

        channelService = new ChannelService(mockChannelRepository, mockVideoRepository, mockMemberRequestRepository, mockUserRepository);
    }


    @Test
    public void should_return_HTTPStatus_OK_When_Successfully_Get_A_Channel_Information() throws Exception {

        when(mockChannelRepository.findById(1L)).thenReturn(Optional.ofNullable(channel));
        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        Object actualChannel = channelService.getInformation(1L);

        verify(mockChannelRepository, times(1)).findById(1L);
        verify(mockChannelRepository, times(1)).existsById(1L);
        assertThat(actualChannel, is(equalTo(Optional.ofNullable(channel))));
    }

    @Test
    public void should_return_HTTPStatus_NOT_FOUND_When_Channel_Does_Not_Exist() throws Exception {

        when(mockChannelRepository.existsById(1L)).thenReturn(false);

        assertThrows(Exception.class, () -> channelService.getInformation(1l));
        verify(mockChannelRepository, times(1)).existsById(1L);
    }

    @Test
    public void should_return_A_NonEmpty_Set_Of_Videos_For_A_Channel() throws Exception {

        Set<Video> videos = new HashSet<>();
        videos.add(video);
        channel.setVideo(videos);


        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        when(mockChannelRepository.findById(1L)).thenReturn(Optional.ofNullable(channel));
        Object actualListOfVideos = channelService.getVideos(1L);

        verify(mockChannelRepository, times(1)).findById(1L);
        verify(mockChannelRepository, times(1)).existsById(1L);
        assertThat(actualListOfVideos, is(equalTo(videos)));

    }

    @Test
    public void should_return_A_Empty_Set_Of_Videos_For_A_Channel() throws Exception {
        Set<Video> videos = new HashSet<>();
        channel.setVideo(videos);

        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        when(mockChannelRepository.findById(1L)).thenReturn(Optional.ofNullable(channel));

        Object actualListOfVideos = channelService.getVideos(1L);

        verify(mockChannelRepository, times(1)).findById(1L);
        verify(mockChannelRepository, times(1)).existsById(1L);
        assertThat(actualListOfVideos, is(equalTo(videos)));
    }

    @Test
    public void should_return_HTTPStatus_NOT_FOUND_When_Channel_Does_Not_Exist_While_Getting_List_Of_Videos() throws Exception {
        when(mockChannelRepository.existsById(1L)).thenReturn(false);

        assertThrows(Exception.class, () -> channelService.getVideos(1l));
        verify(mockChannelRepository, times(1)).existsById(1L);
    }

    @Test
    void shouldReturnChannelsOwnedByAUserWhenOwnerIDisSent() throws Exception {
        user1.setId(2L);
        Channel channel2 = new Channel(user1, "java", "java channel-1", "gurgaon", "india");
        Channel channel3 = new Channel(user, "python", "python channel", "gurgaon", "india");
        Channel channel4 = new Channel(user1, "python-2", "python channel-2", "gurgaon", "india");

        when(mockUserRepository.existsById(2L)).thenReturn(true);
        when(mockChannelRepository.findByOwnerId(2L)).thenReturn(List.of(channel2, channel4));

        List<Channel> expectedChannelList = new ArrayList<>();
        expectedChannelList.add(channel2);
        expectedChannelList.add(channel4);

        assertThat(expectedChannelList, is(channelService.getChannelsOwned(2L)));
        verify(mockChannelRepository, times(1)).findByOwnerId(2L);
    }

    @Test
    void shouldReturnUserNotFoundWhenUserDoesNotExists() throws Exception {
        channel.setId(1L);

        when(mockUserRepository.existsById(2L)).thenReturn(false);

        assertThrows(Exception.class, () -> channelService.getChannelsOwned(2L));
        verify(mockChannelRepository, times(0)).findByOwnerId(2L);
    }

    @Test
    void shouldReturnChannelDoesNotExistsWhenOwnerIdIsSent() throws Exception {
        user1.setId(2L);

        when(mockUserRepository.existsById(2L)).thenReturn(true);
        when(mockChannelRepository.findByOwnerId(2L)).thenReturn(null);


        assertThat(new ArrayList<>(), is(channelService.getChannelsOwned(2L)));
        verify(mockChannelRepository, times(1)).findByOwnerId(2L);
    }

    @Test
    public void should_return_HTTPStatus_OK_When_Successfully_Created_NewChannel() throws Exception {
        user.setId(1L);
        Set<User> userSet = new HashSet<>();
        CreateChannel createChannel = new CreateChannel("channel1", user.getId(), "desc", "gurgaon", "india", userSet);
        Channel channel1 = new Channel(mockUserRepository.getReferenceById(createChannel.getOwner_id()), createChannel.getName(), createChannel.getDescription(), createChannel.getCity(), createChannel.getCountry());
        channel1.setId(1L);

        when(mockUserRepository.getReferenceById(1l)).thenReturn(user);
        when(mockChannelRepository.getReferenceById(1L)).thenReturn(channel1);
        when(mockUserRepository.existsById(1L)).thenReturn(true);
        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        when(mockChannelRepository.save(Mockito.any(Channel.class))).thenReturn(channel1);

        Channel actualChannel = channelService.addChannel(createChannel);

        verify(mockChannelRepository, times(1)).save(Mockito.any(Channel.class));
    }

    @Test
    public void should_return_A_NonEmpty_Set_Of_Pending_Member_Request_For_A_Channel() throws Exception {

        Set<MemberRequest> memberRequests = new HashSet<>();
        memberRequests.add(memberRequest);
        channel.setMemberRequests(memberRequests);

        when(mockChannelRepository.findById(1L)).thenReturn(Optional.ofNullable(channel));
        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        Object actualListOfUsers = channelService.getPendingRequests(1l);

        verify(mockChannelRepository, times(1)).findById(1L);
        verify(mockChannelRepository, times(1)).existsById(1L);
        assertThat(actualListOfUsers, is(equalTo(memberRequests)));
    }

    @Test
    public void should_return_A_Empty_Set_Of_Pending_Member_Request_For_A_Channel() throws Exception {

        Set<MemberRequest> memberRequests = new HashSet<>();
        channel.setMemberRequests(memberRequests);

        when(mockChannelRepository.findById(1L)).thenReturn(Optional.ofNullable(channel));
        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        Object actualListOfUsers = channelService.getPendingRequests(1l);

        verify(mockChannelRepository, times(1)).findById(1L);
        verify(mockChannelRepository, times(1)).existsById(1L);
        assertThat(actualListOfUsers, is(equalTo(memberRequests)));
    }

    @Test
    public void should_return_HTTPStatus_NOT_FOUND_When_Channel_Does_Not_Exist_While_Getting_Pending_Request() throws Exception {

        when(mockChannelRepository.existsById(1L)).thenReturn(false);

        assertThrows(Exception.class, () -> channelService.getPendingRequests(1l));
        verify(mockChannelRepository, times(1)).existsById(1L);
    }

    @Test
    public void should_return_A_Non_Empty_Set_Of_Members_Of_A_Channel() throws Exception {
        Set<User> users = new HashSet<>();
        users.add(user);
        channel.setMembers(users);

        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        when(mockChannelRepository.findById(1L)).thenReturn(Optional.ofNullable(channel));
        Object actualListOfUsers = channelService.getMemberList(1l);

        verify(mockChannelRepository, times(1)).findById(1L);
        verify(mockChannelRepository, times(1)).existsById(1L);
        assertThat(actualListOfUsers, is(equalTo(users)));
    }

    @Test
    public void should_return_A_Empty_Set_Of_Members_Of_A_Channel() throws Exception {
        Set<User> users = new HashSet<>();
        channel.setMembers(users);

        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        when(mockChannelRepository.findById(1L)).thenReturn(Optional.ofNullable(channel));
        Object actualListOfUsers = channelService.getMemberList(1l);

        verify(mockChannelRepository, times(1)).findById(1L);
        verify(mockChannelRepository, times(1)).existsById(1L);
        assertThat(actualListOfUsers, is(equalTo(users)));
    }

    @Test
    public void should_return_HTTPStatus_NOT_FOUND_When_Channel_Does_Not_Exist_While_Getting_List_Of_Members() throws Exception {

        when(mockChannelRepository.existsById(1L)).thenReturn(false);

        assertThrows(Exception.class, () -> channelService.getMemberList(1l));
        verify(mockChannelRepository, times(1)).existsById(1L);
    }

    @Test
    public void should_return_HTTPStatus_OK_On_Rejecting_Member_Request() throws Exception {

        when(mockMemberRequestRepository.existsById(1L)).thenReturn(true);

        channelService.rejectMemberRequest(1l);

        verify(mockMemberRequestRepository, times(1)).deleteById(1L);
        verify(mockMemberRequestRepository, times(1)).existsById(1L);
    }

    @Test
    public void should_return_HTTPStatus_NOT_FOUND_When_Channel_Does_Not_Exist_While_Rejecting_Member_Request() throws Exception {

        when(mockMemberRequestRepository.existsById(1L)).thenReturn(false);

        assertThrows(Exception.class, () -> channelService.rejectMemberRequest(1l));
        verify(mockMemberRequestRepository, times(1)).existsById(1L);
    }

    @Test
    public void should_return_A_User_On_Accepting_Member_Request() throws Exception {
        Set<User> users = new HashSet<>();
        user1.setId(2L);
        channel.setId(1L);
        users.add(user1);
        channel.setMembers(users);

        when(mockMemberRequestRepository.existsById(1L)).thenReturn(true);
        when(mockMemberRequestRepository.findById(1L)).thenReturn(Optional.ofNullable(memberRequest));
        when(mockChannelRepository.save(Mockito.any(Channel.class))).thenReturn(channel);
        when(mockChannelRepository.getReferenceById(1L)).thenReturn(channel);

        Object actualUser = channelService.acceptMemberRequest(1l);

        verify(mockMemberRequestRepository, times(1)).findById(1L);
        verify(mockMemberRequestRepository, times(1)).existsById(1L);
        verify(mockChannelRepository, times(1)).save(Mockito.any(Channel.class));
        verify(mockMemberRequestRepository, times(1)).deleteById(1L);


        assertThat(actualUser, is(equalTo(user1)));

    }

    @Test
    public void should_return_HTTPStatus_Internal_Server_Error_When_Channel_Does_Not_Exist_While_Accepting_Member_Request() throws Exception {
        channel.setId(1L);
        user.setId(1L);
        Set<User> users = new HashSet<>();
        channel.setMembers(users);
        Channel channel1 = new Channel(user, "channel1", "desc", "gurgaon", "india");
        Set<User> users1 = new HashSet<>();
        channel1.setMembers(users1);

        when(mockMemberRequestRepository.existsById(1L)).thenReturn(true);
        when(mockMemberRequestRepository.findById(1L)).thenReturn(Optional.ofNullable(memberRequest));
        when(mockChannelRepository.save(Mockito.any(Channel.class))).thenReturn(channel1);

        assertThrows(InternalException.class, () -> channelService.acceptMemberRequest(1l));

    }

    @Test
    public void should_return_HTTPStatus_NOT_FOUND_When_Channel_Does_Not_Exist_While_Accepting_Member_Request() throws Exception {
        when(mockMemberRequestRepository.existsById(1L)).thenReturn(false);

        assertThrows(Exception.class, () -> channelService.acceptMemberRequest(1l));
        verify(mockMemberRequestRepository, times(1)).existsById(1L);
    }

}
