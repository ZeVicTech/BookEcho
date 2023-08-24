package com.zerolab.bookecho.controller;

import com.zerolab.bookecho.request.ReviewCreateDto;
import com.zerolab.bookecho.response.ReviewResponseDto;
import com.zerolab.bookecho.service.ReviewService;
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

    //리뷰 게시글 저장
    @PostMapping("/review")
    public Long saveReview(@RequestBody ReviewCreateDto reviewCreateDto){
        return reviewService.save(reviewCreateDto);
    }
}
