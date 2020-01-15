package ru.javabegin.training.library.springlibrary_2.spring.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.training.library.springlibrary_2.dao.impl.AuthorService;
import ru.javabegin.training.library.springlibrary_2.domain.Author;

import java.util.List;

// автоматически все методы класса будут возвращать JSON
@RestController
@RequestMapping (value = "/rest/author") // корневой путь
public class RestAuthor {

    @Autowired
    private AuthorService authorService;


    // получить все записи без сортировки (сортировку уже могут сами выбирать на стороне клиента)
    @RequestMapping("/all")
    public List<Author> getAuthors(){
        return authorService.getAll();
    }


    // возвращает все записи с постраничностью
    @RequestMapping("/allPage")
    public List<Author> allPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize){
        return authorService.getAll(pageNumber, pageSize, "fio", Sort.Direction.ASC).getContent();
    }


    // поиск записей без постраничности (сразу весь список)
    @RequestMapping("/search")
    public List<Author> search(@RequestParam("fio") String fio){
        return authorService.search(fio);
    }

    // поиск записей с постраничностью
    @RequestMapping("/searchPage")
    public List<Author> searchPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("fio") String fio){
        return authorService.search(pageNumber, pageSize, "fio", Sort.Direction.ASC, fio).getContent();// т.к. возвращается объект Page, надо у него вызвать getContent, чтобы получить коллекцию
    }

    @RequestMapping("/get")
    public Author get(@RequestParam("id") long id){
        return authorService.get(id);
    }

    @RequestMapping("/delete")
    public boolean delete(@RequestParam("id") long id){
        authorService.delete(authorService.get(id));// сначала получаем автора по id, потом его удаляем
        return true;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Author author){
        authorService.save(author);
        return true;
    }
}

