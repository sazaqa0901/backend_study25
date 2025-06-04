package com.example.todo_api.todo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class TodoCreateRequest {
    @Length(max = 100, message = "content 길이는 100자 이하입니다.")
    private String content;
    @NotNull(message = "member id 값은 필수입니다.")
    private Long memberId;
}
