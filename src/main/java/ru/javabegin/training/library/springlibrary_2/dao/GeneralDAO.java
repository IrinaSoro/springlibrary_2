package ru.javabegin.training.library.springlibrary_2.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface GeneralDAO<T> {

    List<T> getAll(); //получение всех записей (без постраничности)

    T get(long id); // получение объекта по id
    //Optional<T> get(long id);

    T save(T obj); // обновляет и добавляет объект (один метод на 2 действия)

    void delete(T object); // удаление объекта

    List<T> search(String... searchString); //поиск записей с любым количеством параметров

    List<T> getAll(Sort sort); //получение всех данных с сортировкой результата

    Page<T> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection); //получение всех записей с постраничностью

    Page<T> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String...searchString); //поиск записей с постраничностью


}
