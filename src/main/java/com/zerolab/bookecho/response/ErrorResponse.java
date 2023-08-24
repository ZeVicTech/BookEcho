package com.zerolab.bookecho.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 *     "code":"400",
 *     "message":"잘못된 요청입니다",
 *     "validation":{
 *         "title": "값을 입력해주세요"
 *     }
 * }
 */

@Data
public class ErrorResponse {

    private final String code;
    private final String message;

    private Map<String, String> validation = new HashMap<>();

    public void addValidatoin(String fieldName, String errorMessage){
        this.validation.put(fieldName, errorMessage);
    }
}
