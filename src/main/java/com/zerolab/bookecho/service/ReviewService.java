package com.zerolab.bookecho.service;

import com.zerolab.bookecho.entity.Book;
import com.zerolab.bookecho.entity.Member;
import com.zerolab.bookecho.entity.Review;
import com.zerolab.bookecho.repository.BookRepository;
import com.zerolab.bookecho.repository.MemberRepository;
import com.zerolab.bookecho.repository.ReviewRepository;
import com.zerolab.bookecho.request.ReviewCreateDto;
import com.zerolab.bookecho.response.ReviewResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public List<ReviewResponseDto> findAll(Pageable pageable){
        return reviewRepository.findAll(pageable).stream()
                .map(ReviewResponseDto::of)
                .collect(Collectors.toList());
    }

    public ReviewResponseDto findById(Long id){
        Review review = reviewRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
        return ReviewResponseDto.of(review);
    }

    public Long save(ReviewCreateDto reviewCreateDto){
        //임시 북 생성(책 가져오는 기능이 완성되면 삭제)
        Book book = Book.builder()
                .title("아메리칸 프로메테우스")
                .author("카이 버드")
                .publisher("사이언스 북스")
                .pubdate("20230612")
                .image("https://shopping-phinf.pstatic.net/main_4064133/40641337634.20230823071535.jpg")
                .isbn("9791192908236")
                .build();

        bookRepository.save(book);

        //멤버 생성(회원가입, 로그인 기능이 완성되면 삭제)
        Member member = Member.builder()
                .nickName("헤이즈")
                .loginId("로그인 아이디")
                .password("패스워드")
                .build();

        memberRepository.save(member);

        //리뷰 게시글 저장 로직
        Review review = Review.builder()
                .title(reviewCreateDto.getTitle())
                .content(reviewCreateDto.getContent())
                .starPoint(5)
                .book(book) //임시로 집어넣음
                .member(member) //임시로 집어넣음
                .build();

        reviewRepository.save(review);
        return review.getId();
    }

}
