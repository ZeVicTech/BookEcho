package com.zerolab.bookecho.service;

import com.zerolab.bookecho.domain.Book;
import com.zerolab.bookecho.domain.Member;
import com.zerolab.bookecho.domain.Review;
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
    public Long save(Long memberId, ReviewCreate reviewCreate){
        Member member = memberRepository.findById(memberId)
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
    public ReviewResponseDto edit(Long memberId,Long reviewId, ReviewEdit reviewEdit){

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFound::new);

        //토큰 값 id와 일치하는 확인
        if(!Objects.equals(review.getMember().getId(), memberId)){
            throw new Unauthorized();
        }

        //본래 리뷰와 조인된 책 정보
        Book originalBook = review.getBook();

        //ISBN이 동일한 경우
        if(originalBook.getIsbn().equals(reviewEdit.getBook().getIsbn())) {
            //이미지 url이 바뀌었을 경우 이미지url만 업데이트
            if (!originalBook.getImage().equals(reviewEdit.getBook().getImage()))
                originalBook.editImage(reviewEdit.getBook().getImage());
        }
        //ISBN이 동일하지 않을 경우
        else {
                //Book테이블에서 ISBN번호로 조회한 후 해당 책 엔티티 객체가 있으면 그 책 엔티티 객체로 연관관계 수정, 없으면 새로 저장하고 연관관계 수정
                review.editBook(bookRepository.findByIsbn(reviewEdit.getBook().getIsbn())
                        .orElseGet(()->{
                            return bookRepository.save(reviewEdit.getBook());
                        }));
        }

        //이미지 외 다른 정보들 수정
        review.editDetail(
                reviewEdit.getTitle(),
                reviewEdit.getContent(),
                reviewEdit.getStarPoint()
        );

        //게시글 수정(프론트엔드 쪽 요청에 따라 널이 들어올 시 기존에 있는 데이터를 집어 넣을 수 도 있음)
        //ex) reviewEditDto.geTitle != null ? reviewEditDto.getTitle() : review.getTitle()

        return ReviewResponseDto.of(review);
    }

    //리뷰 삭제
    public void delete(Long memberId,Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFound::new);
        System.out.println(memberId);
        System.out.println(review.getMember().getId());

        //토큰 값 id와 일치하는 확인
        if(!Objects.equals(review.getMember().getId(), memberId))
            throw new Unauthorized();

        reviewRepository.delete(review);
    }


}
