package com.isang.api.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob //DB 에서는 String 형태가 아닌 LongText 형태로 생성되도록 해주는 어노테이션
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
