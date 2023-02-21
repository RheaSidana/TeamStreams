package com.thoughtworks.ts;

import com.thoughtworks.ts.channel.models.Channel;
import com.thoughtworks.ts.channel.models.MemberRequest;
import com.thoughtworks.ts.channel.repository.ChannelRepository;
import com.thoughtworks.ts.channel.repository.MemberRequestRepository;
import com.thoughtworks.ts.channel.service.ChannelService;
import com.thoughtworks.ts.user.models.User;
import com.thoughtworks.ts.user.repository.UserRepository;
import com.thoughtworks.ts.user.service.UserService;
import com.thoughtworks.ts.video.models.Video;
import com.thoughtworks.ts.video.repository.VideoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class DataSeeder {
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final VideoRepository videoRepository;
    private final MemberRequestRepository memberRequestRepository;
    private final ChannelService channelService;
    private final UserService userService;

    public DataSeeder(UserRepository userRepository, ChannelRepository channelRepository, VideoRepository videoRepository, MemberRequestRepository memberRequestRepository) {
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
        this.videoRepository = videoRepository;
        this.memberRequestRepository = memberRequestRepository;
        this.userService = new UserService(userRepository, memberRequestRepository, channelRepository);
        this.channelService = new ChannelService(channelRepository, videoRepository, memberRequestRepository, userRepository);
    }

    //TODO - change IF condition
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

            if (userRepository.findByEmail("test-user-1") == null && userRepository.findByEmail("test-user-2") == null) {
                User user1 = new User("test-user-1", "password1");
                User user2 = new User("test-user-2", "password2");
                User user3 = new User("test-user-3", "password3");
                User user4 = new User("test-user-4", "password4");
                User user5 = new User("test-user-5", "password5");
                user1 = userRepository.save(user1);
                user2 = userRepository.save(user2);
                user3 = userRepository.save(user3);
                user4 = userRepository.save(user4);
                user5 = userRepository.save(user5);


                Channel channel1 = new Channel(user1, "Channel-1", "Test-channel-1", "gurgaon", "india");
                Channel channel2 = new Channel(user2, "Channel-2", "Test-channel-2", "gurgaon", "india");

                Set<User> userSet = new HashSet<>();
                userSet.add(user2);
                userSet.add(user4);
                userSet.add(user5);
                channel1.setMembers(userSet);
                channel1 = channelRepository.save(channel1);
                channel2 = channelRepository.save(channel2);


                Video video1 = new Video("Video-1", "https://thoughtworks.zoom.us/rec/share/NGUSS5hlCXDN-mhq985RvCsMkuuMmFSl5SAfDozN6yJ5_E0QTmfLeSoOjd2QCgUF.HWs6cDJgW0fSYvqI", "f.Y8I6@4", "description video 1", false, channel1);
                Video video2 = new Video("Video-2", "https://thoughtworks.zoom.us/rec/share/sfIdPGpDtQWzeUW8MBtcFRO7PQPO9lIR3bplsN_4MPulNofqjCa5EA69TsXlfqsJ.Iuf8RmqlQorbySMy", "+F^5Ba@9", "description video 2", true, channel1);
                Video video3 = new Video("Video-3", "https://thoughtworks.zoom.us/rec/share/EZDAE85ekgInksPXS1yEXfBpnZG_PBGTd8GUNRN4a0O2zwS6nTD6bNan_kLhHjPa.tETvP6ILdOKam0aX", "w+9K@4sE", "description video 3", false, channel1);
                Video video4 = new Video("Video-4", "https://thoughtworks.zoom.us/rec/share/BrH-0c0ZbAE1v_p_y9RvdHhsJ3hUjwYqmdqVSw7nLLqvPZx3S-XO8uYVV5le2gJG.Wa3ZXsEh43rKRvhZ", "?Z3#vC4x", "description video 4", true, channel1);
                Video video5 = new Video("Video-5", "https://thoughtworks.zoom.us/rec/share/0C6Uo3-ps1nADISf3B7k-2My55eZtvh0xBN6J4y0oft5qB_uuCYNIiX3_WjpRm6t.S5Xl8UFfwZ29s9SG", "B&q4!4Pe", "description video 5", false, channel1);

                Video video6 = new Video("Video-6", "https://thoughtworks.zoom.us/rec/share/5C9eJYI9cqLesenkBYoIX_BZP2fesoBwVj3UPWpTsOOLKAb4Kl1S7P33b0ughFxF.ydAn0aaznn86lyhY", "&+7#V+n7", "description video 6", false, channel2);
                Video video7 = new Video("Video-7", "https://thoughtworks.zoom.us/rec/share/yWW-_s6L59mnUGMEVsXJVo3ks_V14gr9rOJFV_xCs4oAzPiD54BSqp8QAkC9QbU.6YYG6sDFVah_bqXJ", "A%J.1!+%", "description video 7", true, channel2);
                Video video8 = new Video("Video-8", "https://thoughtworks.zoom.us/rec/share/TiuRYjKd6D4GKPQzCcRL0whFIS-2JDwtIts1c-LCJahwW6tNAU1S0jjQjbyo2UhU.CqH81FiOzezJATWY", "S2wta2i+", "description video 8", false, channel2);
                Video video9 = new Video("Video-9", "https://thoughtworks.zoom.us/rec/share/VeQWqPAn1_CsgXoRqTPHTah0vpRISSsuBQW7F0gYNEC8NgUz1BZeyV-iguwSYX5M.wFWhEawHzBePYZZS", "*H4wD0wJ", "description video 9", true, channel2);
                Video video10 = new Video("Video-10", "https://thoughtworks.zoom.us/rec/share/_75AkH0H10cgrTFEaajEpzzo5o2Tbj-5fhLFz82EOCyduo2YyOTyxJFvbZjMcSc.KxeATuXkAPA0ivGF", "E1t0=Q3v", "description video 10", false, channel2);

                videoRepository.saveAll(List.of(video1, video2, video3, video4, video5, video6, video7, video8, video9, video10));

                MemberRequest memberRequest = new MemberRequest(user1, channel2);
                memberRequestRepository.save(memberRequest);
                MemberRequest memberRequest2 = new MemberRequest(user3, channel2);
                memberRequestRepository.save(memberRequest2);
            }
        };
    }

}

