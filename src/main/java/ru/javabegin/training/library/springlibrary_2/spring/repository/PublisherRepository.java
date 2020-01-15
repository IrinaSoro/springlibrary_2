package ru.javabegin.training.library.springlibrary_2.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.javabegin.training.library.springlibrary_2.domain.Publisher;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @RestResource(path = "findByName")
    List<Publisher> findByNameContainingIgnoreCaseOrderByName(String name);

    @RestResource(path = "findByNamePage")
    Page<Publisher> findByNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);

}