package com.glanner.core.domain.user;

import com.glanner.core.repository.LogRepository;
import com.glanner.core.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LogTest {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testLogCreate() throws Exception{
        //given
        User user = userRepository.findByEmail("cherish8513@naver.com").orElseThrow(() -> new IllegalStateException("없는 회원 입니다."));

        Log log = Log.builder()
                .url("a")
                .content("d")
                .user(user)
                .build();
        //when
        Log savedLog = logRepository.save(log);

        //then
        Assertions.assertThat(savedLog.getUser()).isEqualTo(user);
    }
}
