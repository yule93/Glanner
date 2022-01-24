package com.glanner.api.dto.request;

import com.glanner.core.domain.user.User;
import com.glanner.core.domain.user.UserRoleStatus;
import lombok.Builder;

public class UserSaveReqDto {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private UserRoleStatus role  = UserRoleStatus.ROLE_USER;
    private String interests;

    @Builder
    public UserSaveReqDto(String name, String email, String password, String phoneNumber, UserRoleStatus role, String interests) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.interests = interests;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .role(role)
                .interests(interests)
                .build();
    }
}
