package com.zerolab.bookecho.request;

import lombok.Data;

@Data
public class SignupDto {

    private String nickName;
    private String loginId;
    private String password;
}
