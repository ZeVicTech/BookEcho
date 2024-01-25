package com.zerolab.bookecho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    @DisplayName("/Post 요청시 Hello World!! 출력 확인")
//    void test() throws Exception {
//        //expected
//        mockMvc.perform(get("/posts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"title\":\"제목입니다\", \"content\":\"내용입니다\"}")
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().string("Hello World!!"))
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//    @Test
//    @DisplayName("/Post 요청시 title값은 필수다.")
//    void test2() throws Exception {
//        //expected
//        mockMvc.perform(get("/posts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"title\":null, \"content\":\"내용입니다\"}")
//                )
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
//                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
//                .andDo(MockMvcResultHandlers.print());
//    }
}