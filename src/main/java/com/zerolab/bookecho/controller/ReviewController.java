package com.zerolab.bookecho.controller;

import com.zerolab.bookecho.config.data.UserSession;
import com.zerolab.bookecho.request.ReviewCreateDto;
import com.zerolab.bookecho.request.ReviewEditDto;
import com.zerolab.bookecho.response.ReviewResponseDto;
import com.zerolab.bookecho.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //인증 테스트
    @GetMapping("/foo")
    public Long foo(UserSession userSession){
        log.info(">>>{}",userSession.getId());
        return userSession.getId();
    }

    //리뷰 게시글 저장
    @PostMapping("/review")
    public Long saveReview(UserSession userSession, @RequestBody @Valid ReviewCreateDto reviewCreateDto){
        return reviewService.save(userSession.getId(), reviewCreateDto);
    }

    //리뷰 게시글 전체 조회
    @GetMapping("/review")
    public List<ReviewResponseDto> findAllReview(Pageable pageable){
        return reviewService.findAll(pageable);
    }

    //리뷰 게시글 단건 조회
    @GetMapping("/review/{reviewId}")
    public ReviewResponseDto findAllReview(@PathVariable Long reviewId){
        return reviewService.findById(reviewId);
    }

    //리뷰 게시글 수정
    @PatchMapping("/review/{reviewId}")
    public ReviewResponseDto editReview(UserSession userSession, @PathVariable Long reviewId, @RequestBody @Valid ReviewEditDto reviewEditDto){
        return reviewService.edit(userSession.getId(), reviewId, reviewEditDto);
    }

    //리뷰 게시글 삭제
    @DeleteMapping("/review/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId){
        reviewService.delete(reviewId);
    }
}
