package com.isang.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class PostCreate {

    @NotBlank(message = "제목이 비어있으면 안된당께요.")
    private String title;

    @NotBlank(message = "내용도 넣어주시면 감사하겠습니다.")
    private String content;


    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 빌더 패턴의 장점?
    // - 가독성이 좋다.
    // - 필요한 값만 받을 수 있다.
    // - 객체의 불변성을 유지할 수 있다.
    // - 빌더 오버로딩이 가능한 조건에 대해서 확인해보자.
}

