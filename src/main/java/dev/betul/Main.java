package dev.betul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args){
        System.out.println("Hello world!");
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/")
    public GreetResponse greet(){
        return new GreetResponse("Hello", List.of("Java", "Python", "JavaScript"), new Person("Beth", 27, 35_000));
    }

    record Person(String name, int age, double savings){}

    record GreetResponse(String greet, List<String> favProgrammingLanguages, Person person){}
}

