package com.glanner.api.service;

import com.glanner.api.dto.request.AddPlannerWorkReqDto;
import com.glanner.api.dto.request.SaveUserReqDto;
import com.glanner.api.dto.response.FindPlannerWorkResDto;
import com.glanner.api.exception.UserNotFoundException;
import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.domain.user.Schedule;
import com.glanner.core.domain.user.User;
import com.glanner.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;

    @Transactional
    public Long saveUser(SaveUserReqDto reqDto){
        User user = reqDto.toEntity();

        validateDuplicateMember(user);

        user.changePassword(passwordEncoder.encode(user.getPassword()));


        Schedule schedule = Schedule.builder()
                .build();

        user.changeSchedule(schedule);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public List<FindPlannerWorkResDto> getWorks(String userEmail, LocalDateTime month) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        return userQueryRepository.findDailyWorks(user.getSchedule().getId(), month);
    }

    @Override
    public void addWork(String userEmail, AddPlannerWorkReqDto requestDto) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        user.getSchedule().addDailyWork(requestDto.toEntity());
    }

    private void validateDuplicateMember(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(m -> {throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }

}
