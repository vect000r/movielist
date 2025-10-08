package com.vect0r.movielist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MovielistApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovielistApplication.class, args);
    }

    @GetMapping("home")
    public String helloWorld() {
        return "Hello World!";
    }

}
