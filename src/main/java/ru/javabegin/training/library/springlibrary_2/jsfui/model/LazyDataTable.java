package ru.javabegin.training.library.springlibrary_2.jsfui.model;


import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.javabegin.training.library.springlibrary_2.jsfui.controller.AbstractController;

import java.util.List;
import java.util.Map;


// модель для постраничного вывода списка книг при любом поиске
// можно применять не только к книгам, но и к любым типам данных, т.к. используется Generics
@Getter
@Setter
public class LazyDataTable<T> extends LazyDataModel<T> {

    private AbstractController<T> abstractController;

    public LazyDataTable(AbstractController<T> abstractController) {
        this.abstractController = abstractController;
    } //abstractController хранит ссылку на переданный контроллер


    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) { //вызывается, когда делаем какие-то манипуляции на странице JSF (н: выбираем, сколько показать книг на странице)

        int pageNumber = first / pageSize;// считываем номер страницы

        Sort.Direction sortDirection = Sort.Direction.ASC;// по-умолчанию - сортировка по возрастанию

        if (sortOrder!=null) {
            // все текущие настройки DataTable (сортировка, поле сортировки) будут передаваться в SQL запрос
            switch (sortOrder) {
                case DESCENDING:
                    sortDirection = Sort.Direction.DESC;
                    break;
            }
        }

        Page<T> searchResult = abstractController.search(pageNumber, pageSize, sortField, sortDirection); //вызывает сервисный уровень и возвращает результат в searchResult

        this.setRowCount((int) searchResult.getTotalElements()); //setRowCount -указываем, сколько строк было найдено, чтобы (он) правильно распределил все служебные данные; вызываем getTotalElements метод у searchResult

        return searchResult.getContent(); //возвращаем List типа Book
    }




}

