package ru.javabegin.training.library.springlibrary_2.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.training.library.springlibrary_2.dao.impl.GenreService;
import ru.javabegin.training.library.springlibrary_2.domain.Genre;

import java.util.List;

// автоматически все методы класса будут возвращать JSON
@RestController
@RequestMapping (value = "/rest/genre") // корневой путь
public class RestGenre {

    @Autowired
    private GenreService genreService;


    // получить все записи без сортировки (сортировку уже могут сами выбирать на стороне клиента)
    @RequestMapping("/all")
    public List<Genre> getGenres(){
        return genreService.getAll();
    }


    // возвращает всезаписи с постраничностью
    @RequestMapping("/allPage")
    public List<Genre> allPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize){
        return genreService.getAll(pageNumber, pageSize, "fio", Sort.Direction.ASC).getContent();
    }


    // поиск записей без постраничности (сразу весь список)
    @RequestMapping("/search")
    public List<Genre> search(@RequestParam("name") String name){
        return genreService.search(name);
    }

    // поиск записей с постраничностью
    @RequestMapping("/searchPage")
    public List<Genre> searchPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("fio") String fio){
        return genreService.search(pageNumber, pageSize, "fio", Sort.Direction.ASC, fio).getContent(); // т.к. возвращается объект Page, надо у него вызвать getContent, чтобы получить коллекцию
    }

    // получение объекта по id
    @RequestMapping("/get")
    public Genre get(@RequestParam("id") long id){
        return genreService.get(id);
    }

    // удаление объекта по id
    @RequestMapping("/delete")
    public boolean delete(@RequestParam("id") long id){
        genreService.delete(genreService.get(id));// сначала получаем объект по id, потом его удаляем
        return true;
    }

    // добавление переданного объекта
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Genre genre){
        genreService.save(genre);
        return true;
    }



}
