package com.glanner.api.service;

import com.glanner.api.dto.request.*;
import com.glanner.api.queryrepository.GlannerQueryRepository;
import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.domain.glanner.DailyWorkGlanner;
import com.glanner.core.domain.glanner.Glanner;
import com.glanner.core.domain.glanner.UserGlanner;
import com.glanner.core.domain.user.User;
import com.glanner.core.repository.DailyWorkGlannerRepository;
import com.glanner.core.repository.GlannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GlannerServiceImpl implements GlannerService{
    private final UserQueryRepository userQueryRepository;
    private final GlannerRepository glannerRepository;
    private final GlannerQueryRepository glannerQueryRepository;
    private final DailyWorkGlannerRepository dailyWorkGlannerRepository;

    private static final int MAX_PERSONNEL_SIZE = 5;

    @Override
    public void saveGlanner(String hostEmail) {

        User findUser = getUser(userQueryRepository.findByEmail(hostEmail));
        Glanner glanner = Glanner.builder()
                .host(findUser)
                .build();

        UserGlanner userGlanner = UserGlanner.builder()
                .user(findUser)
                .build();

        glanner.addUserGlanner(userGlanner);

        Glanner savedGlanner = glannerRepository.save(glanner);
    }

    @Override
    public void deleteGlanner(DeleteGlannerReqDto reqDto) {
        Glanner findGlanner = getGlanner(glannerRepository.findById(reqDto.getGlannerId()));

        glannerQueryRepository.deleteAllWorksById(findGlanner.getId());
        glannerQueryRepository.deleteAllUserGlannerById(findGlanner.getId());
        glannerRepository.delete(findGlanner);
    }

    @Override
    public void addUser(AddUserToGlannerReqDto reqDto, String hostEmail) {

        User host = getUser(userQueryRepository.findByEmail(hostEmail));
        User attendingUser = getUser(userQueryRepository.findByEmail(reqDto.getEmail()));
        Glanner findGlanner = getGlanner(glannerQueryRepository.findByHostId(host.getId()));
        if (findGlanner.getUserGlanners().size() >= MAX_PERSONNEL_SIZE){
            throw new IllegalStateException("회원 수가 가득 찼습니다.");
        }
        UserGlanner userGlanner = UserGlanner
                .builder()
                .user(attendingUser)
                .build();

        findGlanner.addUserGlanner(userGlanner);
    }

    @Override
    public void deleteUser(DeleteUserFromGlannerReqDto reqDto) {
        User attendingUser = getUser(userQueryRepository.findByEmail(reqDto.getEmail()));
        Glanner findGlanner = getGlanner(glannerQueryRepository.findById(reqDto.getGlannerId()));
        int size = findGlanner.getUserGlanners().size();
        for (int i = 0; i < size; i++) {
            if(findGlanner.getUserGlanners().get(i).getUser().getId().equals(attendingUser.getId())){
                findGlanner.getUserGlanners().get(i).delete();
                break;
            }
        }
    }

    @Override
    public void addDailyWork(AddGlannerWorkReqDto reqDto) {
        Glanner glanner = getGlanner(glannerQueryRepository.findById(reqDto.getGlannerId()));
        glanner.addDailyWork(reqDto.toEntity());
    }

    @Override
    public void deleteDailyWork(DeleteGlannerWorkReqDto reqDto) {
        Glanner findGlanner = getGlanner(glannerQueryRepository.findById(reqDto.getGlannerId()));
        DailyWorkGlanner deleteWork = dailyWorkGlannerRepository.findById(reqDto.getWorkId()).orElseThrow(IllegalArgumentException::new);
        findGlanner.getWorks().remove(deleteWork);
    }

    @Override
    public void updateDailyWork(UpdateGlannerWorkReqDto reqDto) {
        DailyWorkGlanner updateWork = dailyWorkGlannerRepository.findById(reqDto.getWorkId()).orElseThrow(IllegalArgumentException::new);
        updateWork.changeDailyWork(reqDto.getStartTime(), reqDto.getEndTime(), reqDto.getTitle(), reqDto.getContent());
    }

    public User getUser(Optional<User> user){
        return user.orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }

    public Glanner getGlanner(Optional<Glanner> glanner){
        return glanner.orElseThrow(() -> new IllegalArgumentException("글래너가 존재하지 않습니다."));
    }
}
