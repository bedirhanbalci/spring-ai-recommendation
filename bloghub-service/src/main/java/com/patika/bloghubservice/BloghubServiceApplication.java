package com.patika.bloghubservice;

import com.patika.bloghubservice.dto.request.BlogSaveRequest;
import com.patika.bloghubservice.dto.request.UserSaveRequest;
import com.patika.bloghubservice.dto.response.UserResponse;
import com.patika.bloghubservice.service.BlogService;
import com.patika.bloghubservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class BloghubServiceApplication implements CommandLineRunner {
    private final UserService userService;
    private final BlogService blogService;


    @Override
    public void run(String... args) {
        UserResponse userResponse = userService.saveUser(new UserSaveRequest("Adem", "ademozalp57@gmail.com", "12345678"));

        String email = userResponse.getEmail();

        blogService.createBlog(new BlogSaveRequest(email, "Hide and Seek with Collections in Java", "This blog is a thought experiment. I’m revisiting some classic ideas and exploring some new ideas. I have some gut feelings, that I am trying to validate through code examples. Patience. I think there is something important in here."));
        blogService.createBlog(new BlogSaveRequest(email, "Java 21 Virtual Threads - Dude, Where’s My Lock?", "Netflix has an extensive history of using Java as our primary programming language across our vast fleet of microservices. As we pick up newer versions of Java, our JVM Ecosystem team seeks out new language features that can improve the ergonomics and performance of our systems."));
        blogService.createBlog(new BlogSaveRequest(email, "Java Collections: Best Practices", "In this blog, we'll delve into the best practices for using Java Collections efficiently. We'll cover various collection types, their use cases, and performance considerations."));
        blogService.createBlog(new BlogSaveRequest(email, "Understanding Spring Boot Annotations", "Spring Boot provides a plethora of annotations to simplify development. This blog explains key annotations and how they can be used to streamline your Spring Boot applications."));
        blogService.createBlog(new BlogSaveRequest(email, "Java Streams API: A Comprehensive Guide", "The Streams API is a powerful feature introduced in Java 8. This blog explores the Streams API, its methods, and how it can be used to write clean and efficient code."));
        blogService.createBlog(new BlogSaveRequest(email, "Dependency Injection in Spring", "Dependency Injection is a core concept in Spring. In this blog, we will understand what Dependency Injection is, how it works in Spring, and its advantages in creating maintainable code."));
        blogService.createBlog(new BlogSaveRequest(email, "Effective Error Handling in Spring Boot", "Error handling is crucial in any application. This blog covers the techniques and best practices for handling errors in Spring Boot applications to provide a robust user experience."));
        blogService.createBlog(new BlogSaveRequest(email, "Building RESTful APIs with Spring Boot", "RESTful APIs are a standard in web development. In this blog, we'll learn how to create RESTful APIs using Spring Boot, covering everything from setup to deployment."));
        blogService.createBlog(new BlogSaveRequest(email, "Introduction to Microservices with Spring Cloud", "Microservices architecture is gaining popularity for its scalability and resilience. This blog introduces Spring Cloud and how it simplifies the creation of microservices."));
        blogService.createBlog(new BlogSaveRequest(email, "Testing Spring Applications: A Practical Guide", "Testing is a critical aspect of development. This blog provides a practical guide to testing Spring applications, covering unit tests, integration tests, and best practices."));

    }

    public static void main(String[] args) {
        SpringApplication.run(BloghubServiceApplication.class, args);
    }

}
