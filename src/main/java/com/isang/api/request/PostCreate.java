package com.isang.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class PostCreate {

    @NotBlank(message = "제목이 비어있으면 안된당께요.")
    private String title;

    @NotBlank(message = "내용도 넣어주시면 감사하겠습니다.")
    private String content;

}
