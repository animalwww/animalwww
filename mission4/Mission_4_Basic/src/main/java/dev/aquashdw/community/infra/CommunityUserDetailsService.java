package dev.aquashdw.community.infra;

import dev.aquashdw.community.entity.UserEntity;
import dev.aquashdw.community.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CommunityUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CommunityUserDetailsService.class);
                                                                                                                        private final UserRepository userRepository;
                                                                                                                        private final PasswordEncoder passwordEncoder;

    public CommunityUserDetailsService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
   //     final UserEntity testUserEntity = new UserEntity();
 //       testUserEntity.setUsername("entity_user");
 //       testUserEntity.setPassword(passwordEncoder.encode("test1pass"));
//        this.userRepository.save(testUserEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<UserEntity> userEntity = this.userRepository.findByUsername(username);

        UserEntity userEntity2 = userEntity.orElseThrow(() -> {
            throw new UsernameNotFoundException(String.format("error :: username %s not found", username));
        });

        return new User(username, userEntity2.getPassword(), new ArrayList<>());
    }
}
