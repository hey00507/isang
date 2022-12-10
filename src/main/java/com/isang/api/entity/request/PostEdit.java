package com.isang.api.entity.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class PostEdit {
// 기능이 다를 경우, 클래스를 꼭 나누자 -> 고려하지 않으면 추후에 분리가 필요할 때 귀찮아짐
    @NotBlank(message = "타이틀 입력")
    private String title;

    @NotBlank(message = "컨텐츠 입력")
    private String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
