package com.example.todo_api.todo;

import com.example.todo_api.Member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoRepository {
    @PersistenceContext
    private EntityManager em;

    //생성
    public void save(Todo todo){
        em.persist(todo);
    }
    //조회
    //단건 조회(한 개의 데이터 조회)
    public Todo find(Long todoId){
        return em.find(Todo.class, todoId);
    }
    //다건 조회(여러 개의 데이터 조회)
    public List<Todo> findAll(){
        return em.createQuery("select from Todo as t", Todo.class).getResultList();
    }
    //조건 조회
    public List<Todo> findAllByMember(Member member){
        return em.createQuery("select t from Todo as t where t.member = member", Todo.class).getResultList()
                .setParameter("todo_member", member)
                .getResultList();
    }
    //수정
    //삭제
    //TEST 용도로만 사용 !!
    public void flushAndClear(){
        em.flush();
        em.clear();
    }
}
