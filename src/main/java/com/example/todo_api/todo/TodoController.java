package com.example.todo_api.todo;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @ApiResponse(responseCode = "400", description = "존재하지 않는 멤버일 때 응답")
    @PostMapping
    public ResponseEntity<Void> createTodo(@RequestBody @Valid TodoCreateRequest request){
        Long todoId = todoService.createTodo(request.getContent(), request.getMemberId);
        return ResponseEntity.created(URI.create("/todo/" + todoId)).build();
    }


    @PatchMapping("/{todoId}")
    public ResponseEntity<Void> updateTodo(@RequestBody TodoUpdateRequest request, @PathVariable Long todoId){
        todoService.updateTodo(request.getMemberId(), todoId, request.getNewConetent());
        return ResponseEntity.ok().build();

    }

    @GetMapping("/list")
    public ResponseEntity<List<Todo>> getTodoList(@RequestBody Long memberId){
        List<todo> todos = todoService.findMyTodos(memberId);
        return ResponseEntity.ok(todos);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@RequestBody Long memberId, @PathVariable Long todoId){
        todoService.deleteTodo(memberId, todoId);
        return ResponseEntity.noContent().build();
    }
}