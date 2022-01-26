package com.glanner.api.controller;

import com.glanner.api.dto.request.*;
import com.glanner.api.dto.response.BaseResponseEntity;
import com.glanner.api.dto.response.FindGlannerHostResDto;
import com.glanner.api.dto.response.FindGlannerWorkResDto;
import com.glanner.api.exception.UserNotFoundException;
import com.glanner.api.queryrepository.DailyWorkGlannerQueryRepository;
import com.glanner.api.queryrepository.GlannerQueryRepository;
import com.glanner.api.service.GlannerService;
import com.glanner.core.domain.glanner.DailyWorkGlanner;
import com.glanner.core.domain.glanner.Glanner;
import com.glanner.core.repository.GlannerRepository;
import com.glanner.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/glanner")
@RequiredArgsConstructor
public class GlannerController {
    private final GlannerService glannerService;
    private final GlannerQueryRepository glannerQueryRepository;
    private final DailyWorkGlannerQueryRepository dailyWorkQueryRepository;

    @PostMapping("/save")
    public ResponseEntity<BaseResponseEntity> createGlanner(){
        String hostEmail = getUsername(SecurityUtils.getCurrentUsername());
        glannerService.saveGlanner(hostEmail);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponseEntity> deleteGlanner(@RequestBody @Valid DeleteGlannerReqDto reqDto){
        glannerService.deleteGlanner(reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @GetMapping("/get-host-id")
    public ResponseEntity<BaseResponseEntity> findHost(@RequestBody @Valid FindGlannerHostReqDto reqDto){
        Glanner findGlanner = glannerQueryRepository.findById(reqDto.getGlannerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글래너 입니다."));
        FindGlannerHostResDto responseDto = new FindGlannerHostResDto(200, "Success", findGlanner.getHost().getId());
        return ResponseEntity.status(200).body(responseDto);
    }

    @PostMapping("/add-user")
    public ResponseEntity<BaseResponseEntity> addUser(@RequestBody @Valid AddUserToGlannerReqDto reqDto){
        String hostEmail = getUsername(SecurityUtils.getCurrentUsername());
        glannerService.addUser(reqDto, hostEmail);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<BaseResponseEntity> deleteUser(@RequestBody @Valid DeleteUserFromGlannerReqDto reqDto){
        glannerService.deleteUser(reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @PostMapping("/add-work")
    public ResponseEntity<BaseResponseEntity> addWork(@RequestBody @Valid AddGlannerWorkReqDto reqDto){
        glannerService.addDailyWork(reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @DeleteMapping("/delete-work")
    public ResponseEntity<BaseResponseEntity> deleteWork(@RequestBody @Valid DeleteGlannerWorkReqDto reqDto){
        glannerService.deleteDailyWork(reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @PutMapping("/update-work")
    public ResponseEntity<BaseResponseEntity> updateWork(@RequestBody @Valid UpdateGlannerWorkReqDto reqDto){
        glannerService.updateDailyWork(reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

    @GetMapping("/find-work")
    public ResponseEntity<List<FindGlannerWorkResDto>> findWork(@RequestBody @Valid FindGlannerWorkReqDto reqDto){
        List<FindGlannerWorkResDto> findGlannerWorkResDtos = dailyWorkQueryRepository.findByGlannerIdWithDate(
                reqDto.getGlannerId(),
                reqDto.getStartTime(),
                reqDto.getEndTime());
        return ResponseEntity.status(200).body(findGlannerWorkResDtos);
    }

    public String getUsername(Optional<String> username){
        return username.orElseThrow(UserNotFoundException::new);
    }
}
