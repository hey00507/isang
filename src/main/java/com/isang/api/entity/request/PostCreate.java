package com.isang.api.entity.request;

import com.isang.common.exception.custom.InvalidRequest;
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

    public void validate(){
        if(this.title.contains("바보")){
            throw new InvalidRequest("title", "제목에 바보를 포함할 수 없습니다.");
        }
    }
}


