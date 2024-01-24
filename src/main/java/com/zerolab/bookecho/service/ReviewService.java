package com.zerolab.bookecho.service;

import com.zerolab.bookecho.domain.Book;
import com.zerolab.bookecho.domain.Member;
import com.zerolab.bookecho.domain.Review;
import com.zerolab.bookecho.exception.BookNotFound;
import com.zerolab.bookecho.exception.ReviewNotFound;
import com.zerolab.bookecho.exception.Unauthorized;
import com.zerolab.bookecho.repository.BookRepository;
import com.zerolab.bookecho.repository.MemberRepository;
import com.zerolab.bookecho.repository.ReviewRepository;
import com.zerolab.bookecho.request.ReviewCreate;
import com.zerolab.bookecho.request.ReviewEdit;
import com.zerolab.bookecho.response.ReviewResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    //리뷰 전체 조회
    public List<ReviewResponseDto> findAll(Pageable pageable){
        return reviewRepository.findAll(pageable).stream()
                .map(ReviewResponseDto::of)
                .collect(Collectors.toList());
    }

    //리뷰 단건 조회
    public ReviewResponseDto findById(Long id){
        Review review = reviewRepository.findById(id)
                .orElseThrow(ReviewNotFound::new);

        return ReviewResponseDto.of(review);
    }

    //리뷰 저장
    //TODO: sessionId보다는 memberId라고 변수명을 만드는게 좋을듯
    public Long save(Long sessionId, ReviewCreate reviewCreate){
        Member member = memberRepository.findById(sessionId)
                .orElseThrow(Unauthorized::new);

        //책 정보 저장 로직
        Book book = Book.builder()
                .title(reviewCreate.getBook().getTitle())
                .author(reviewCreate.getBook().getAuthor())
                .publisher(reviewCreate.getBook().getAuthor())
                .pubdate(reviewCreate.getBook().getPubdate())
                .image(reviewCreate.getBook().getImage())
                .isbn(reviewCreate.getBook().getIsbn())
                .build();

        bookRepository.save(book);

        //리뷰 게시글 저장 로직
        Review review = Review.builder()
                .title(reviewCreate.getTitle())
                .content(reviewCreate.getContent())
                .starPoint(reviewCreate.getStarPoint())
                .createDateTime(LocalDateTime.now())
                .book(book)
                .member(member)
                .build();

        reviewRepository.save(review);

        return review.getId();
    }

    //리뷰 수정
    public ReviewResponseDto edit(Long sessionId,Long reviewId, ReviewEdit reviewEdit){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFound::new);

        Book book = bookRepository.findById(review.getBook().getId())
                .orElseThrow(BookNotFound::new);



        //토큰 값 id와 일치하는 확인
        if(!Objects.equals(review.getMember().getId(), sessionId)){
            throw new Unauthorized();
        }

        //수정된 리뷰의 책이 있는지 조회
//        Book book = bookRepository.findById(reviewEdit.getBookId())
//                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 책입니다."));

        //게시글 수정(프론트엔드 쪽 요청에 따라 널이 들어올 시 기존에 있는 데이터를 집어 넣을 수 도 있음)
        //ex) reviewEditDto.geTitle != null ? reviewEditDto.getTitle() : review.getTitle()

        review.edit(
                reviewEdit.getTitle(),
                reviewEdit.getContent(),
                reviewEdit.getStarPoint(),
                reviewEdit.getBook()
        );

        return ReviewResponseDto.of(review);
    }

    //리뷰 삭제
    public void delete(Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFound::new);

        reviewRepository.delete(review);
    }


}
