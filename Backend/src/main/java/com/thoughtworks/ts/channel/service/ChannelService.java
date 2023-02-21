package com.thoughtworks.ts.channel.service;

import com.sun.jdi.InternalException;
import com.thoughtworks.ts.channel.DTO.CreateChannel;
import com.thoughtworks.ts.channel.models.Channel;
import com.thoughtworks.ts.channel.models.MemberRequest;
import com.thoughtworks.ts.channel.repository.ChannelRepository;
import com.thoughtworks.ts.channel.repository.MemberRequestRepository;
import com.thoughtworks.ts.user.models.User;
import com.thoughtworks.ts.user.repository.UserRepository;
import com.thoughtworks.ts.video.models.Video;
import com.thoughtworks.ts.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@ComponentScan("com.thoughtworks.ts.channel.service.ChannelRepository")
public class ChannelService {

    private ChannelRepository channelRepository;
    private VideoRepository videoRepository;
    private MemberRequestRepository memberRequestRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository, VideoRepository videoRepository, MemberRequestRepository memberRequestRepository,
                          UserRepository userRepository) {
        this.channelRepository = channelRepository;
        this.videoRepository = videoRepository;
        this.memberRequestRepository = memberRequestRepository;
        this.userRepository = userRepository;
    }

    public Object getInformation(long id) throws Exception {
        if (!channelRepository.existsById(id)) {
            throw new Exception("Channel Id does not exist");
        }
        return channelRepository.findById(id);
    }


    public Set<Video> getVideos(long id) throws Exception {
        if (!channelRepository.existsById(id)) {
            throw new Exception("Channel Id does not exist");
        }
//        List<Video> videos = videoRepository.findAllVideosByChannel_Id(id);
        return channelRepository.findById(id).get().getVideo();
    }

    public Set<MemberRequest> getPendingRequests(long id) throws Exception {
        if (!channelRepository.existsById(id)) {
            throw new Exception("Channel Id does not exist");
        }
//        List<MemberRequest> memberRequests = memberRequestRepository.findMemberRequestsByChannelId(id);
        return channelRepository.findById(id).get().getMemberRequests();
    }

    public List<Channel> getChannelsOwned(long owner_id) throws Exception {

        if (!userRepository.existsById(owner_id)) {
            throw new Exception("User not found");
        }


        List<Channel> channelsOwned = channelRepository.findByOwnerId(owner_id);

        if (channelsOwned == null) {
            return new ArrayList<>();
        }
        return channelsOwned;
    }

    public Channel addChannel(CreateChannel createChannel) throws Exception {
        Channel channel1 = new Channel();
        channel1.setName(createChannel.getName());
        channel1.setUser(userRepository.getReferenceById(createChannel.getOwner_id()));
        channel1.setDescription(createChannel.getDescription());
        channel1.setCity(createChannel.getCity());
        channel1.setCountry(createChannel.getCountry());
        if (createChannel.getMembers() == null) {
            Set<User> userSet = new HashSet<>();
            channel1.setMembers(userSet);
        } else {
            channel1.setMembers(createChannel.getMembers());
        }

        if (userRepository.existsById(createChannel.getOwner_id()) == false) {
            throw new Exception("User Not Found");
        }

        Channel c = channelRepository.save(channel1);
        return c;
    }

    public Set<User> getMemberList(long id) throws Exception {
        if (!channelRepository.existsById(id)) {
            throw new Exception("Channel Id does not exist");
        }
        Channel channel = channelRepository.findById(id).get();

        return channel.getMembers();
    }

    public Object acceptMemberRequest(long member_request_id) throws Exception {
        Set<User> userSet = new HashSet<>();
        if (!memberRequestRepository.existsById(member_request_id)) {
            throw new Exception("Member Request Id does not exist");
        }
        MemberRequest member_request = memberRequestRepository.findById(member_request_id).get();

        Channel channel = member_request.getChannel();
        User user = member_request.getUser();

        userSet = channel.getMembers();
        userSet.add(user);
        channel.setMembers(userSet);

        Channel savedChannel = channelRepository.save(channel);

        if (savedChannel.getMembers().contains(user)) {
            memberRequestRepository.deleteById(member_request_id);
            return user;
        } else {
            throw new InternalException("User not added in channel as member");
        }
    }

    public Object rejectMemberRequest(long member_request_id) throws Exception {
        if (!memberRequestRepository.existsById(member_request_id)) {
            throw new Exception("Member Request Id does not exist");
        }

        memberRequestRepository.deleteById(member_request_id);
        return "Member Request rejected successfully";
    }
}
