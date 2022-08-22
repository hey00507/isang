package com.isang.api.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // 엔티티에는 절대로 서비스의 정책을 기입하지 말 것 , 서비스 정책에 맞는 응답 클래스를 생성하자
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob //DB 에서는 String 형태가 아닌 LongText 형태로 생성되도록 해주는 어노테이션
    private String content;



    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
