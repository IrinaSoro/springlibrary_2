package ru.javabegin.training.library.springlibrary_2.jsfui.controller;


import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.javabegin.training.library.springlibrary_2.dao.PublisherDAO;
import ru.javabegin.training.library.springlibrary_2.domain.Publisher;
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
public class PublisherController extends AbstractController<Publisher> {


    // из JSF таблицы обязательно должна быть ссылки на переменные, иначе при использовании постраничности dataTable работает некорректно
    // также - выбранное пользователем значение (кол-во записей на странице) будет сохраняться
    private int rowsCount = 20;
    private int first;
    private Page<Publisher> publisherPages;

    @Autowired
    private PublisherDAO publisherDao;


    private Publisher selectedPublisher;


    private LazyDataTable<Publisher> lazyModel;

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable(this);

    }



    public void save() {
        publisherDao.save(selectedPublisher);
        RequestContext.getCurrentInstance().execute("PF('dialogPublisher').hide()");
    }

    @Override
    public Page<Publisher> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {



        return publisherPages;

    }

    @Override
    public void addAction() {
        selectedPublisher = new Publisher();

        showEditDialog();

    }


    @Override
    public void editAction() {

        // выбранный publisher уже будет записан в переменную selectedPublisher (как только пользователь кликнет на редактирование)
        // он отобразится в диалоговом окне
        showEditDialog();

    }

    @Override
    public void deleteAction() {

        // выбранный publisher уже будет записан в переменную selectedPublisher (как только пользователь кликнет на удаление)
        publisherDao.delete(selectedPublisher);
    }

    private void showEditDialog() {

        // показывает диалоговое окно со значениями selectedPublisher
        RequestContext.getCurrentInstance().execute("PF('dialogPublisher').show()");
    }

    public List<Publisher> find(String name) {
        return publisherDao.search(name);
    }
}