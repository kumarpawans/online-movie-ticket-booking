package com.psp.test.auth.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UserDTO {

    private final long id;
    private final String email;
    @NonNull
    private final String name;
    @NonNull
    private final String avatarHash;
    private final Boolean isFollowedByMe;

}
