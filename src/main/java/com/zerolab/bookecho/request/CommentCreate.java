package com.zerolab.bookecho.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentCreate {

    @NotBlank
    private String content;
}
