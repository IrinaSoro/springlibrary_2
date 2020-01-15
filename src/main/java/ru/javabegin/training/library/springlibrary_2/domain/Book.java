package ru.javabegin.training.library.springlibrary_2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Table(catalog = "library")
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
@Setter @Getter
@EqualsAndHashCode(of = "id")
public class Book {

    public Book(){

    }

    public Book(Long id, String name, Integer pageCount, String isbn, Genre genre, Author author, Publisher publisher, Integer publishYear,
                byte[] image, String descr, long viewCount, long totalRating, long totalVoteCount, int avgRating) {
        this.id =id;
        this.name = name;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.image = image;
        this.descr = descr;
        this.viewCount = viewCount;
        this.totalRating = totalRating;
        this.totalVoteCount = totalVoteCount;
        this.avgRating = avgRating;
    }

    public Book(Long id, byte[] image) {
        this.id= id;
        this.image = image;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore // не добавлять значение в Json
    @Lob //напрмер, чтобы реализовать лению загрузгу
    @Column(updatable = false)
    private byte[] content; //byte - двоичное значение, которое потом буем конвертировать в нужный формат

    @Column(name = "page_count")
    private Integer pageCount;

    private String isbn;



    @ManyToOne
    @JoinColumn
    private Genre genre;


    @ManyToOne //двусторонняя связь книги-автор
    @JoinColumn //будем получать готовый объект, а не их id (т.е. н: не id 20, а Борис Акунин
    private Author author;

    @ManyToOne
    @JoinColumn
    private Publisher publisher;


    @Column(name = "publish_year")
    private Integer publishYear;


    private byte[] image;

    private String descr;

    @Column(name = "view_count")
    private long viewCount;

    @Column(name = "total_rating")
    private long totalRating;

    @Column(name = "total_vote_count")
    private long totalVoteCount;

    @Column(name = "avg_rating")
    private int avgRating;

    @Override
    public String toString(){
        return name;
    }






}
