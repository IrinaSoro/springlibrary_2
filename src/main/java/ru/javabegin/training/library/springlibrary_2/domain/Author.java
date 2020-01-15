package ru.javabegin.training.library.springlibrary_2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;



//JPA
@Entity // все поля класса будут автоматически связаны со столбцами таблицы
@Table (catalog = "library")
@EqualsAndHashCode(of = "id") //создает хэш-код по id и сравнивает по id тоже благодаря lombok библиотеки
@Getter @Setter //благодаря библиотеки lombok мы можем сократить код н: просто указать аннотации на геттер-сеттер, а не прописывать их
@DynamicUpdate //update и insert нужны для того, чтобы когда мы будем вставлять новые объекты в таблицу (или обновлять), онбудет использ только те поля, которые изменились
@DynamicInsert
@SelectBeforeUpdate //чтобы hibernate мог проверить перед update-ом, а нужно ли объект обновлять (если мы вызываем update)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //автоинкремент поля
    private Long id;

    private String fio;

    private Date birthday;

    @JsonIgnore
    @Basic(fetch =FetchType.LAZY)
    @OneToMany(mappedBy = "author")
    private List<Book> books;

    @Override
    public String toString(){
        return fio;
    }


}
