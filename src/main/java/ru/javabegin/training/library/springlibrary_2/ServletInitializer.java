package ru.javabegin.training.library.springlibrary_2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy // включаем использование AspectJ
//@EnableAutoConfiguration
//@ComponentScan(basePackages = {"ru.javabegin.training.library.springlibrary_2"}) //уже не требуется указывать
public class ServletInitializer extends SpringBootServletInitializer {
   /* public static void main(String[] args) {
        SpringApplication.run(ServletInitializer.class, args);
    }*/


}