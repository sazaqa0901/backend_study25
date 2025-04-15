package com.example.todo_api.hw;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@Getter
@Component
@RequiredArgsConstructor
public class MyController {
   private final MyService myService;

   public void printController() {
       System.out.println("controller");
       myService.printService();
    }
}
