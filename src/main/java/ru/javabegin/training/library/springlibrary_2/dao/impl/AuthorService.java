package ru.javabegin.training.library.springlibrary_2.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javabegin.training.library.springlibrary_2.dao.*;
import ru.javabegin.training.library.springlibrary_2.domain.Author;
import ru.javabegin.training.library.springlibrary_2.spring.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

// сервисный уровень для работы с авторами
// API для реализованных бизнес процессов
// код должен обращаться только через Service (не к Repository напрямую)
@Service // сервисный Spring бин
@Transactional
// методы помечаются как транзакционные (для запросов применяются настройки транзакций по-умолчанию, уровень изоляции и пр.)
public class AuthorService implements AuthorDAO {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public List<Author> getAll(Sort sort) {
        return authorRepository.findAll(sort);
    }



    @Override
    public Page<Author> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return authorRepository.findAll( PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }

    @Override
    public List<Author> search(String... searchString) {
        return authorRepository.findByFioContainingIgnoreCaseOrderByFio(searchString[0]);
    }

    @Override
    public Page<Author> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString) {
        return authorRepository.findByFioContainingIgnoreCaseOrderByFio(searchString[0], PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField)));
    }


    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void delete(Author author) {
        authorRepository.delete(author);
    }

    @Override
    public Author get(long id) {
        Optional<Author> bookmark = authorRepository.findById(id); // Optional - обертка, в котором может быть значение или пусто (используется для исключение ошибки NullPointerException
        if (bookmark.isPresent()) {  // если значение представлено - вернуть его
            return bookmark.get();
        } else {
            return null;
        }
    }




}
