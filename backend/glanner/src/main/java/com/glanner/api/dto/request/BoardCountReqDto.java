package com.glanner.api.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardCountReqDto {
    private String countType;

    @Builder
    public BoardCountReqDto(String countType) {
        this.countType = countType;
    }
}
