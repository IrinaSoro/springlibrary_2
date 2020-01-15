package ru.javabegin.training.library.springlibrary_2.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.javabegin.training.library.springlibrary_2.domain.Book;

import java.util.List;

public interface BookDAO extends GeneralDAO<Book> {

    List<Book> findTopBooks(int limit); //поиск топовых книг

    byte[] getContent(long id); // получение контента по id

    Page<Book> findByGenre(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, long genreId); //постраничный вывод книг определенного жанра

    // обновление статистики просмотра книги
    void updateViewCount(long viewCount, long id);

    // обновить данные рейтинга
    void updateRating(long totalRating, long totalVoteCount, int avgRating, long id);
}
