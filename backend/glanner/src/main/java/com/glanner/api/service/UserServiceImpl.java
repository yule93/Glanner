package com.glanner.api.service;

import com.glanner.api.dto.request.UserSaveReqDto;
import com.glanner.core.domain.user.Schedule;
import com.glanner.core.domain.user.User;
import com.glanner.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Long saveUser(UserSaveReqDto reqDto){
        User user = reqDto.toEntity();

        user.changePassword(passwordEncoder.encode(user.getPassword()));

        validateDuplicateMember(user);

        Schedule schedule = Schedule.builder()
                .build();

        user.changeSchedule(schedule);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    private void validateDuplicateMember(User user) {
        System.out.println(user.getEmail());
        userRepository.findByEmail(user.getEmail())
                .ifPresent(m -> {throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }

}
