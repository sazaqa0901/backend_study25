package com.example.todo_api.todo;

import com.example.todo_api.Member.Member;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.misc.LogManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void todoSaveTest(){
        //트랜잭션의 시작
        Todo todo = new Todo("todo content", false, null);
        todoRepository.save(todo);

        //트랜젹션 종료 => 커밋
        //에러가 발생했을 때는 자동으로 롤백

        Assertions.assertThat(todo.getId()).isNotNull();
    }
    @Test
    @Transactional
    void todoFindOneByIdTest(){
        //given
        Todo todo = new Todo("todo content", false, null);
        todoRepository.save(todo);

        todoRepository.flushAndClear();

        //when
        Todo findTodo = todoRepository.findById(todo.getId());
        //them
        Assertions.assertThat(findTodo.getId()).isEqualTo(todo.getId());
    }
    @Test
    @Transactional
    void todoFindAllTest(){
        Todo todo1 = new Todo("todo content", false, null);
        Todo todo2 = new Todo("todo content", false, null);
        Todo todo3 = new Todo("todo content", false, null);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        List<Todo> todoList = todoRepository.findAll();

        Assertions.assertThat(todoList).hasSize(3);
    }
    @Test
    @Transactional
    void  todoFindAllMemberTest(){
        Member member1 = new Member();
        Member member2 = new Member();
        Member member3 = new Member();

        memberRepository.save(member1);
        memberRepository.save(member2);

        Todo todo1 = new Todo("todo content", false, member1);
        Todo todo2 = new Todo("todo content", false, member2);
        Todo todo3 = new Todo("todo content", false, member3);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        List<Todo> member1TodoList = todoRepository.findAllByMember(member1);
        Assertions.assertThat(member1TodoList).hasSize(2);
    }
    @AfterAll
    public static void doNotFinish(){
        System.out.println("test finished");
        while(true){}
    }
}
