package com.thoughtworks.ts.user.service;

import com.thoughtworks.ts.channel.models.Channel;
import com.thoughtworks.ts.channel.models.MemberRequest;
import com.thoughtworks.ts.channel.repository.ChannelRepository;
import com.thoughtworks.ts.channel.repository.MemberRequestRepository;
import com.thoughtworks.ts.exceptions.InvalidRequestException;
import com.thoughtworks.ts.user.models.User;
import com.thoughtworks.ts.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private MemberRequestRepository memberRequestRepository;
    private ChannelRepository channelRepository;

    @Autowired
    public UserService(
            UserRepository userRepository,
            MemberRequestRepository memberRequestRepository,
            ChannelRepository channelRepository) {
        this.userRepository = userRepository;
        this.memberRequestRepository = memberRequestRepository;
        this.channelRepository = channelRepository;
    }

    public MemberRequest addRequest(Long user_id, Long channel_id) throws Exception {
        if (!channelRepository.existsById(channel_id)) {
            throw new Exception("Channel id does not exist");
        }
        if (!userRepository.existsById(user_id)) {
            throw new Exception("User id does not exist");
        }

        User user = userRepository.findById(user_id).get();
        Channel channel = channelRepository.findById(channel_id).get();

        if (channelRepository.getReferenceById(channel.getId()).getUser().getId() == user_id) {
            throw new InvalidRequestException("User id and Owner id are same");
        }
        if (channel.getMembers().contains(user)) {
            throw new InvalidRequestException("User is already a member of the channel");
        }

        if (memberRequestRepository.existsMemberRequestWithChannelIdAndUserId(user_id, channel_id) != null) {
            throw new InvalidRequestException("Member request already sent");
        }
        MemberRequest memberRequest = new MemberRequest(user, channel);
        memberRequestRepository.save(memberRequest);
        return memberRequest;
    }

    public User getUserDetails(Long user_Id) throws Exception {
        if (userRepository.existsById(user_Id) == false) {
            throw new Exception("User Not Found");
        }

        return userRepository.findById(user_Id).get();
    }


    public Object getChannelsJoinedAsMembers(Long user_id) throws Exception {
        if (userRepository.existsById(user_id) == false) {
            throw new Exception("User Not Found");
        }

        User user = userRepository.findById(user_id).get();
        Set<Channel> channelList = user.getListOfChannels();

        return channelList;
    }

    public Object getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "email"));
    }
}
