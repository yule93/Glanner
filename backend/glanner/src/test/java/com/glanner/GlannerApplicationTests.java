package com.glanner;

import com.glanner.core.domain.user.Schedule;
import com.glanner.core.domain.user.User;
import com.glanner.core.domain.user.UserRoleStatus;
import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class GlannerApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Transactional
	@Test
	void contextLoads() {
		User user = User.builder()
				.phoneNumber("010-6575-2938")
				.email("cherish8514@naver.com")
				.name("JeongJooHeon")
				.password("1234")
				.role(UserRoleStatus.ROLE_USER)
				.build();

		Schedule schedule = Schedule.builder()
				.build();
		user.changeSchedule(schedule);
		userRepository.save(user);
	}

}
