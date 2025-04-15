package com.example.todo_api.hw;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Getter
@Component
@RequiredArgsConstructor
public class MyService {
    private final MyRepository myRepository;

    public void printService(){
        System.out.println("service");
        myRepository.printRepository();
    }
}
