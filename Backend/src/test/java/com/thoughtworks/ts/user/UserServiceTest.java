package com.thoughtworks.ts.user;

import com.thoughtworks.ts.channel.models.Channel;
import com.thoughtworks.ts.channel.models.MemberRequest;
import com.thoughtworks.ts.channel.repository.ChannelRepository;
import com.thoughtworks.ts.channel.repository.MemberRequestRepository;
import com.thoughtworks.ts.exceptions.InvalidRequestException;
import com.thoughtworks.ts.user.models.User;
import com.thoughtworks.ts.user.repository.UserRepository;
import com.thoughtworks.ts.user.service.UserService;
import com.thoughtworks.ts.video.models.Video;
import com.thoughtworks.ts.video.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private ChannelRepository mockChannelRepository;
    private VideoRepository mockVideoRepository;
    private MemberRequestRepository mockMemberRequestRepository;
    private UserRepository mockUserRepository;
    private UserService userService;
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
        user1 = new User("test-user-2@test.com", "pass");
        channel = new Channel(user, "channel1", "desc", "gurgaon", "india");
        user.setId(1L);
        user1.setId(2L);
        channel.setId(1L);
        memberRequest = new MemberRequest(user1, channel);
        userService = new UserService(mockUserRepository, mockMemberRequestRepository, mockChannelRepository);
    }

    @Test
    public void should_return_HTTPStatus_OK_When_Successfully_Add_A_Member_Request() throws Exception {

        Set<User> users = new HashSet<>();
        channel.setMembers(users);

        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        when(mockUserRepository.existsById(2L)).thenReturn(true);
        when(mockUserRepository.findById(2L)).thenReturn(Optional.ofNullable(user1));
        when(mockChannelRepository.findById(1L)).thenReturn(Optional.ofNullable(channel));
        when(mockChannelRepository.getReferenceById(1L)).thenReturn(channel);
        when(mockMemberRequestRepository.save(Mockito.any(MemberRequest.class))).thenReturn(memberRequest);

        userService.addRequest(2L, 1L);

        verify(mockChannelRepository, times(1)).existsById(1L);
        verify(mockUserRepository, times(1)).existsById(2L);
        verify(mockChannelRepository, times(1)).findById(1L);
        verify(mockUserRepository, times(1)).findById(2L);
        verify(mockChannelRepository, times(1)).getReferenceById(1L);
        verify(mockMemberRequestRepository, times(1)).save(Mockito.any(MemberRequest.class));

    }

    @Test
    public void should_return_HTTPStatus_NOT_FOUND_When_Channel_Does_Not_Exist_While_Adding_A_Member_Request() throws Exception {

        when(mockChannelRepository.existsById(1L)).thenReturn(false);
        when(mockUserRepository.existsById(1L)).thenReturn(true);
        assertThrows(Exception.class, () -> userService.addRequest(1l, 1l));
        verify(mockChannelRepository, times(1)).existsById(1L);

    }

    @Test
    public void should_return_HTTPStatus_NOT_FOUND_When_User_Does_Not_Exist_While_Adding_A_Member_Request() throws Exception {

        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        when(mockUserRepository.existsById(1L)).thenReturn(false);

        assertThrows(Exception.class, () -> userService.addRequest(1l, 1l));
        verify(mockUserRepository, times(1)).existsById(1L);

    }

    @Test
    public void should_return_HTTPStatus_FORBIDDEN_When_User_ID_And_Owner_Id_Are_Same_While_Adding_A_Member_Request() throws Exception {

        user.setId(1L);
        channel.setId(1L);


        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        when(mockUserRepository.existsById(1L)).thenReturn(true);
        when(mockUserRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(mockChannelRepository.findById(1L)).thenReturn(Optional.ofNullable(channel));
        when(mockChannelRepository.getReferenceById(1L)).thenReturn(channel);
        when(mockMemberRequestRepository.save(Mockito.any(MemberRequest.class))).thenReturn(memberRequest);

        assertThrows(InvalidRequestException.class, () -> userService.addRequest(1l, 1l));
        verify(mockChannelRepository, times(1)).existsById(1L);
        verify(mockUserRepository, times(1)).existsById(1L);
        verify(mockChannelRepository, times(1)).findById(1L);
        verify(mockUserRepository, times(1)).findById(1L);
        verify(mockChannelRepository, times(1)).getReferenceById(1L);


    }

    @Test
    public void should_return_HTTPStatus_FORBIDDEN_When_User_Is_Already_Member_While_Adding_A_Member_Request() throws Exception {
        User user1 = new User("test1@gmail.com", "pass");
        user1.setId(1L);
        channel.setId(1L);
        Set<User> users = new HashSet<>();
        users.add(user1);
        channel.setMembers(users);

        MemberRequest memberRequest1 = new MemberRequest(user1, channel);

        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        when(mockUserRepository.existsById(1L)).thenReturn(true);
        when(mockUserRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        when(mockChannelRepository.findById(1L)).thenReturn(Optional.ofNullable(channel));

        when(mockChannelRepository.getReferenceById(1L)).thenReturn(channel);
        when(mockMemberRequestRepository.save(Mockito.any(MemberRequest.class))).thenReturn(memberRequest1);

        assertThrows(InvalidRequestException.class, () -> userService.addRequest(1l, 1l));
        verify(mockChannelRepository, times(1)).existsById(1L);
        verify(mockUserRepository, times(1)).existsById(1L);
        verify(mockChannelRepository, times(1)).findById(1L);
        verify(mockUserRepository, times(1)).findById(1L);
        verify(mockChannelRepository, times(1)).getReferenceById(1L);

    }

    @Test
    public void should_return_HTTPStatus_FORBIDDEN_When_Member_Request_Already_Exists_While_Adding_A_Member_Request() throws Exception {

        user.setId(1L);
        channel.setId(1L);
        User user1 = new User("test@test.com", "pass");
        user1.setId(2L);
        Set<User> users = new HashSet<>();
        channel.setMembers(users);
        MemberRequest memberRequest1 = new MemberRequest(user1, channel);

        when(mockChannelRepository.existsById(1L)).thenReturn(true);
        when(mockUserRepository.existsById(2L)).thenReturn(true);
        when(mockUserRepository.findById(2L)).thenReturn(Optional.ofNullable(user1));
        when(mockChannelRepository.findById(1L)).thenReturn(Optional.ofNullable(channel));
        when(mockChannelRepository.getReferenceById(1L)).thenReturn(channel);
        when(mockMemberRequestRepository.existsMemberRequestWithChannelIdAndUserId(2L, 1L)).thenReturn(memberRequest1);

        assertThrows(InvalidRequestException.class, () -> userService.addRequest(2l, 1l));
        verify(mockChannelRepository, times(1)).existsById(1L);
        verify(mockUserRepository, times(1)).existsById(2L);
        verify(mockChannelRepository, times(1)).findById(1L);
        verify(mockUserRepository, times(1)).findById(2L);
        verify(mockChannelRepository, times(1)).getReferenceById(1L);
        verify(mockMemberRequestRepository, times(1)).existsMemberRequestWithChannelIdAndUserId(2L, 1L);

    }

    @Test
    void should_Return_User_Details_When_UserID_Is_Sent() throws Exception {
        user.setId(1L);

        when(mockUserRepository.existsById(1L)).thenReturn(true);
        when(mockUserRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        assertEquals(userService.getUserDetails(1L), user);
        verify(mockUserRepository, times(1)).existsById(1L);
        verify(mockUserRepository, times(1)).findById(1L);
    }

    @Test
    void should_Throw_UserNotFound_Exception_When_UserID_Is_Sent() throws Exception {
        when(mockUserRepository.existsById(2L)).thenReturn(false);

        assertThrows(Exception.class, () -> userService.getUserDetails(2L));
        verify(mockUserRepository, times(1)).existsById(2L);
        verify(mockUserRepository, times(0)).findById(2L);
    }

}
