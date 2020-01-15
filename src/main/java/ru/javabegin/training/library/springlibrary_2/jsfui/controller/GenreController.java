package ru.javabegin.training.library.springlibrary_2.jsfui.controller;


import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.javabegin.training.library.springlibrary_2.dao.GenreDAO;
import ru.javabegin.training.library.springlibrary_2.domain.Genre;
import ru.javabegin.training.library.springlibrary_2.jsfui.model.LazyDataTable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
@Getter
@Setter
public class GenreController extends AbstractController<Genre> {



    // из JSF таблицы обязательно должна быть ссылки на переменные, иначе при использовании постраничности dataTable работает некорректно
    // также - выбранное пользователем значение (кол-во записей на странице) будет сохраняться
    private int rowsCount = 20;
    private int first;

    @Autowired
    private GenreDAO genreDao;

    private Genre selectedGenre;

    private LazyDataTable<Genre> lazyModel;

    private Page<Genre> genrePages;


    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable(this);

    }


    public List<Genre> find(String name) {
        return genreDao.search(name);
    }







    public void save() {
        genreDao.save(selectedGenre);
        RequestContext.getCurrentInstance().execute("PF('dialogGenre').hide()");
    }

    @Override
    public Page<Genre> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {

        return genrePages;

    }

    @Override
    public void addAction() {

    }

    @Override
    public void editAction() {

    }


    @Override
    public void deleteAction() {

    }


    // вызывается для отображения всех жанров слева на странице
    public List<Genre> getAll() {
        return genreDao.getAll(Sort.by(Sort.Direction.ASC, "name"));
    }


}


