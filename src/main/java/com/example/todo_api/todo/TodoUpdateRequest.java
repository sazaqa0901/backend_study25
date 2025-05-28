package com.example.todo_api.todo;

import lombok.Getter;

@Getter
public class TodoUpdateRequest {
    private String newConetent;
    private Long memberId;
}
