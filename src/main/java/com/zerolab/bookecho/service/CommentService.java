package com.zerolab.bookecho.service;

import com.zerolab.bookecho.domain.Comment;
import com.zerolab.bookecho.domain.Member;
import com.zerolab.bookecho.domain.Review;
import com.zerolab.bookecho.exception.*;
import com.zerolab.bookecho.repository.CommentRepository;
import com.zerolab.bookecho.repository.MemberRepository;
import com.zerolab.bookecho.repository.ReviewRepository;
import com.zerolab.bookecho.request.CommentCreate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    //댓글 등록 로직
    public Long save(Long memberId, Long reviewId, CommentCreate commentCreate){
        //멤버 조회(해당하는 멤버가 없을 경우 권한 관련 오류처리)
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFound::new);
        //리뷰 조회(해당하는 리뷰가 없을 경우 리뷰 관련 오류처리)
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFound::new);

        //댓글 저장
        Comment comment = Comment.builder()
                .content(commentCreate.getContent())
                .member(member)
                .review(review)
                .createDateTime(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

        return comment.getId();
    }

    //댓글 삭제 로직
    public void delete(Long memberId, Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFound::new);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFound::new);

        //댓글 작성자와 삭제 요청한 회원이 일치하지 않을 경우 예외처리
        if(!comment.getMember().equals(member))
            throw new InvalidRequest();

        commentRepository.delete(comment);
    }
}
