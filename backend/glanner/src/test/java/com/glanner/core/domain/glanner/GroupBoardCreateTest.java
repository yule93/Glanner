package com.glanner.core.domain.glanner;

import com.glanner.core.domain.user.User;
import com.glanner.core.domain.user.UserRoleStatus;
import com.glanner.core.repository.GroupBoardRepository;
import com.glanner.core.repository.UserGlannerRepository;
import com.glanner.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class GroupBoardCreateTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupBoardRepository groupBoardRepository;
    @Autowired
    private UserGlannerRepository userGlannerRepository;

    @Test
    public void testCreateGroupBoard() throws Exception{
        //given 테스트 준비 : 어떤 상황이 주어진다.
        User user = userRepository.findByEmail("asdf@naver.com").orElseThrow(() -> new IllegalStateException("없는 유저 입니다."));
//        System.out.println("사용자 이름!!!!!!!!: " + user);

        GroupBoard groupBoard = GroupBoard.builder()
                .user(user)
                .title("그룹 보드 제목2")
                .content("그룹 보드 내용2")
                .interests("#관심사#")
                .fileUrls(null)
                .count(0)
                .build();

        //when 테스트 실행 : 어떤 일이 발생한다.
        GroupBoard savedGroupBoard = groupBoardRepository.save(groupBoard);

        //then 테스트 검증 : 특정 결과를 기다린다
        assertThat(savedGroupBoard.getTitle()).isEqualTo("그룹 보드 제목2");
        assertThat(savedGroupBoard.getContent()).isEqualTo("그룹 보드 내용2");
        assertThat(savedGroupBoard.getInterests()).isEqualTo("#관심사#");
        assertThat(savedGroupBoard.getCount()).isEqualTo(0);

    }

    @Test
    public void testCreateUserGlanner() throws Exception{
        //given
        User user = User.builder()
                .phoneNumber("010-6575-2938")
                .email("asdf@naver.com")
                .name("JeongJooHeon")
                .interests("#난그게재밌더라강식당다시보기#")
                .password("1234")
                .role(UserRoleStatus.ROLE_USER)
                .schedule(null)
                .build();

        UserGlanner userGlanner = UserGlanner.builder()
                .glanner(null)
                .user(null)
                .build();
        //when
//        User savedUser = userRepository.save(user);
        UserGlanner savedUserGlanner = userGlannerRepository.save(userGlanner);

        //then
        assertThat(savedUserGlanner.getGlanner()).isEqualTo(null);
    }

}