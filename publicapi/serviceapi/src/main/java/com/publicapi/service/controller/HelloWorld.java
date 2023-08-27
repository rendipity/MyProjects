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
    @PostMapping("/student")
    public Student getStudent(@RequestBody Student student){
        System.out.println(student);
        return new Student(student.getId()+100,student.getName(),student.getAge()+10);
    }
}
