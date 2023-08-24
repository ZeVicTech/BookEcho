package com.zerolab.bookecho.dummy;

import com.zerolab.bookecho.entity.Book;
import com.zerolab.bookecho.entity.Comment;
import com.zerolab.bookecho.entity.Member;
import com.zerolab.bookecho.entity.Review;
import com.zerolab.bookecho.repository.BookRepository;
import com.zerolab.bookecho.repository.CommentRepository;
import com.zerolab.bookecho.repository.MemberRepository;
import com.zerolab.bookecho.repository.ReviewRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDummy {

    private final InitDummyService initDummyService;

    @PostConstruct
    public void init(){
        initDummyService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitDummyService{

        private final EntityManager em;
        private final MemberRepository memberRepository;
        private final BookRepository bookRepository;
        private final ReviewRepository reviewRepository;
        private final CommentRepository commentRepository;

        @Transactional
        public void init(){
            //member와 book 10개 생성
            for(int i = 1;i<11;i++){
                memberRepository.save(Member.builder()
                        .nickName("member_"+i)
                        .loginId("member_"+i+"_loginId")
                        .password("member_"+i+"_password")
                        .build()
                );

                bookRepository.save(Book.builder()
                        .title("book_"+i)
                        .author("author_"+i)
                        .image("https://shopping-phinf.pstatic.net/main_4064133/40641337634.20230823071535.jpg")
                        .isbn("9791192908236_"+i)
                        .pubdate("20230612_"+i)
                        .publisher("publisher_"+i)
                        .build()
                );
            }

            //review 10개 생성
            for(int i = 1;i<11;i++){
                reviewRepository.save(Review.builder()
                        .title("review_"+i)
                        .content("review_"+i+"_content")
                        .starPoint(5)
                        .createDateTime(LocalDateTime.now())
                        .member(memberRepository.findById(Long.valueOf(i)).get())
                        .book(bookRepository.findById(Long.valueOf(i)).get())
                        .build()
                );
            }

            //comment 10개 생성 각각의 리뷰에 생성
            for(int i = 1; i<11;i++){
                commentRepository.save(Comment.builder()
                        .review(reviewRepository.findById(Long.valueOf(i)).get())
                        .member(memberRepository.findById(Long.valueOf(11-i)).get())
                        .content("comment_"+i+"_content")
                        .createDateTime(LocalDateTime.now())
                        .build()
                );
            }
        }
    }
}
