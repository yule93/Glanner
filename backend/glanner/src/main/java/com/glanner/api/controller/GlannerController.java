package com.glanner.api.controller;

import com.glanner.api.dto.request.AddGlannerWorkReqDto;
import com.glanner.api.dto.request.AddUserToGlannerReqDto;
import com.glanner.api.dto.request.UpdateGlannerWorkReqDto;
import com.glanner.api.dto.response.BaseResponseEntity;
import com.glanner.api.dto.response.FindGlannerHostResDto;
import com.glanner.api.dto.response.FindGlannerWorkResDto;
import com.glanner.api.exception.UserNotFoundException;
import com.glanner.api.queryrepository.DailyWorkGlannerQueryRepository;
import com.glanner.api.service.GlannerService;
import com.glanner.core.domain.glanner.Glanner;
import com.glanner.core.domain.user.User;
import com.glanner.core.repository.GlannerRepository;
import com.glanner.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/glanner")
@RequiredArgsConstructor
public class GlannerController {
    private final GlannerService glannerService;
    private final GlannerRepository glannerRepository;
    private final DailyWorkGlannerQueryRepository dailyWorkQueryRepository;

    @PostMapping
    public ResponseEntity<BaseResponseEntity> createGlanner(){
        String hostEmail = getUsername(SecurityUtils.getCurrentUsername());
        glannerService.saveGlanner(hostEmail);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseEntity> deleteGlanner(@PathVariable Long id){
        glannerService.deleteGlanner(id);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseEntity> findHost(@PathVariable Long id){
        Glanner findGlanner = glannerRepository.findRealById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글래너 입니다."));
        User host = findGlanner.getHost();
        return ResponseEntity.status(200).body(new FindGlannerHostResDto(host.getId(), host.getName(), host.getEmail()));
    }

    @PostMapping("/user")
    public ResponseEntity<BaseResponseEntity> addUser(@RequestBody @Valid AddUserToGlannerReqDto reqDto){
        String hostEmail = getUsername(SecurityUtils.getCurrentUsername());
        glannerService.addUser(reqDto, hostEmail);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @DeleteMapping("/user/{glannerId}/{userId}")
    public ResponseEntity<BaseResponseEntity> deleteUser(@PathVariable Long glannerId, @PathVariable Long userId){
        glannerService.deleteUser(glannerId, userId);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @PostMapping("/work")
    public ResponseEntity<BaseResponseEntity> addWork(@RequestBody @Valid AddGlannerWorkReqDto reqDto){
        glannerService.addDailyWork(reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @DeleteMapping("/work/{glannerId}/{workId}")
    public ResponseEntity<BaseResponseEntity> deleteWork(@PathVariable Long glannerId, @PathVariable Long workId){
        glannerService.deleteDailyWork(glannerId, workId);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @PutMapping("/work")
    public ResponseEntity<BaseResponseEntity> updateWork(@RequestBody @Valid UpdateGlannerWorkReqDto reqDto){
        glannerService.updateDailyWork(reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @GetMapping("/work/{glannerId}/{startTime}/{endTime}")
    public ResponseEntity<List<FindGlannerWorkResDto>> findWork(@PathVariable  Long glannerId,
                                                                @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") LocalDateTime startTime,
                                                                @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") LocalDateTime endTime){

        List<FindGlannerWorkResDto> findGlannerWorkResDtos =
                dailyWorkQueryRepository.findByGlannerIdWithDate(glannerId, startTime, endTime);
        return ResponseEntity.status(200).body(findGlannerWorkResDtos);
    }

    public String getUsername(Optional<String> username){
        return username.orElseThrow(UserNotFoundException::new);
    }
}
