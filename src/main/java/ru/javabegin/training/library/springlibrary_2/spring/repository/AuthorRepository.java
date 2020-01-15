package ru.javabegin.training.library.springlibrary_2.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.javabegin.training.library.springlibrary_2.domain.Author;

import java.util.List;

@RepositoryRestResource
@Repository // специальный Spring bean, который помечает интерфейс как Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {// JpaRepository - содержит CRUD функционал + постраничность

    @RestResource(path = "findByFio")
    // на основании имени метода будет построен Hibernate запрос
    List<Author> findByFioContainingIgnoreCaseOrderByFio(String fio);

    @RestResource(path = "findByFioPage")
    // Page содержит результаты выполнения запроса и служебные данные для постраничности
    Page<Author> findByFioContainingIgnoreCaseOrderByFio(String fio, Pageable pageable); // Pageable - параметры для постраничности

}
