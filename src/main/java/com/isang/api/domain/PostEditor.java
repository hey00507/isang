package com.isang.api.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * Editor : 도메인에 위치, 수정을 할 수 있는 필드들만 따로 정의해줌
 */
@Getter
public class PostEditor {
    private final String title;
    private final String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
