package ru.javabegin.training.library.springlibrary_2.spring.controller;


import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@Log
public class RedirectController {

    /*@Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;*/

    //при запуске проекта - первый запрос попадает сюда
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {

        // перенаправление на страницу индекс
        return "redirect:" + request.getRequestURL().append("/index.xhtml").toString();

      //  bookRepository.findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName("г", "наб");
     //   Page<Book> bookList = bookRepository.findByGenre(15, new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "name")));

      //  List<Author> authorList = authorRepository.findAll();
        //System.out.println(" = ");
/*
        Page<Author> authors = authorService.getAll(0, 10, "fio", Sort.Direction.DESC);

        Author a = new Author();
        a.setFio("тестовый автор");
        a.setBirthday(new Date(1454284800000L));


        Author newAuthor = authorService.save(a);


        return "ok";*/
    }

}
