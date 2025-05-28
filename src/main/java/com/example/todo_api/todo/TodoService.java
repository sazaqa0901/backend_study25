package com.example.todo_api.todo;

import com.example.todo_api.Member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.core.SpringProperties;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service{
    @AliasFor(annotation = Component.class)
    String value() default "";
}
@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;


    private final TodoRepository todoRepository;
    private final UserReposirtory userReposirtory;

    @Transactional
    public void createTodo(String content, Long memberId) throws Exception{
         User user = userReposirtory.findById(memberId);

        if(Member == null){
            throw new Exception("존재하지 않는 유저 ID 입니다.")
        }
        Todo todo = new Todo(content, member);
        todoRepository.save(todo);
        return todo.getId();
    }
    @Transactional(readOnly = true)
    public List<Todo> findMyTodos(Long memberId){
        Member member = memberRepository.findById(userId);

        if (member == null){
            throw new RuntimeException("멤버가 존재하지 않습니다.");
        }

        return todoRepository.findAllByMember(member);
    }

    @Transactional
    public void updateTodo(Long memberId, Long todoId){
        Member member = memberRepository.findById(memberId);

        if (member == null){
            throw new RuntimeException("멤버가 존재하지 않습니다.");
        }

        Todo todo = todoRepository.findById(todoId);

        if (todo == null){
            throw new RuntimeException("할 일이 존재하지 않습니다.")
        }

        if (todo.getMember() != member){
            throw new RuntimeException("할 일은 내가 만든 할 일만 수정할 수 있습니다.");
        }
        todo.updateContent(newContent);
    }

    @Transactional
    public void deleteTodo(Long memberId, Long todoId){
        Member member = memberRepository.findById(memberId);

        if (member == null){
            throw new RuntimeException("멤버가 존재하지 않습니다.");
        }

        Todo todo = todoRepository.findById(todoId);

        if (todo == null){
            throw new RuntimeException("할 일이 존재하지 않습니다.");
        }

        if (todo.getMember() != member){
            throw new RuntimeException("할 일은 내가 만든 할 일만 삭제할 수 있습니다.");
        }

        todoRepository.deleteById(todo.getId());

    }
}
