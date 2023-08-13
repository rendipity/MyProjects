package com.publicapi.service.controller;

import com.publicapi.service.controller.modal.Student;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/serviceapi")
public class HelloWorld {

    @GetMapping("/hello")
    public String helloWorld(String name){
        return "hello, "+name;
    }

    @PostMapping("/hello")
    public String helloWorld_post(String name){
        return "Post: hello, "+name;
    }
    @GetMapping("/student")
    public Student getStudent(String id){
        return new Student(id,"lijie",22);
    }
}
