package com.example.todo_api.hw;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Getter
@Component
@RequiredArgsConstructor
public class MyRepository {

    public void printRepository(){
        System.out.println("repository");
    }
}
