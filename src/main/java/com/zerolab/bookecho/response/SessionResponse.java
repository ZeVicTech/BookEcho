package com.zerolab.bookecho.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SessionResponse {

    private final String nickName;
    private final String accessToken;
}
