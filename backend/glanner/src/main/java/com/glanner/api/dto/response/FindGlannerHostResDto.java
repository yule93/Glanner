package com.glanner.api.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FindGlannerHostResDto extends BaseResponseEntity{

    public Long hostId;

    public FindGlannerHostResDto(int status, String message, Long hostId) {
        super(status, message);
        this.hostId = hostId;
    }

}
