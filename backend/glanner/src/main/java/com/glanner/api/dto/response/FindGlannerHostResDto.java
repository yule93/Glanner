package com.glanner.api.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindGlannerHostResDto extends BaseResponseEntity{

    public Long hostId;
    public String name;
    public String email;
}
