package ru.javabegin.training.library.springlibrary_2.jsfui.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.javabegin.training.library.springlibrary_2.dao.BookDAO;
import ru.javabegin.training.library.springlibrary_2.dao.GenreDAO;
import ru.javabegin.training.library.springlibrary_2.domain.Book;
import ru.javabegin.training.library.springlibrary_2.jsfui.enums.SearchType;
import ru.javabegin.training.library.springlibrary_2.jsfui.model.LazyDataTable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@ManagedBean
@SessionScoped
@Component
@Getter @Setter
@Log
public class BookController extends AbstractController<Book> {

    public static final int DEFAULT_PAGE_SIZE = 20;// по-умолчанию сколько книг отображать на странице
    public static final int TOP_BOOKS_LIMIT = 5;// сколько показывать популярных книг
    // из JSF таблицы обязательно должна быть ссылки на переменные, иначе при использовании постраничности dataGrid работает некорректно (не отрабатывает bean)
    // также - выбранное пользователем значение (кол-во записей на странице) будет сохраняться
    private int rowsCount = DEFAULT_PAGE_SIZE;

    private SearchType searchType; // запоминает последний выбранный вариант поиска


    @Autowired
    private BookDAO bookDao; // будет автоматически подставлен BookService, т.к. Spring контейнер ищет бин по типу


    @Autowired
    private GenreDAO genreDao;


    @Autowired
    private GenreController genreController;

    private Book selectedBook; // ссылка на текущую книгу (которую редактируют, хотят удалять и пр.) - т.е. над какой книгой в данный момент производим действие
    private LazyDataTable<Book> lazyModel; // класс-утилита, которая помогает выводить данные постранично (работает в паре с компонентами на странице JSF)

    private byte[] uploadedImage; // сюда будет сохраняться загруженная пользователем новая обложка (при редактировании или при добавлении книги)
    private byte[] uploadedContent; // сюда будет сохраняться загруженный пользователем PDF контент (при редактировании или при добавлении книги)

    private Page<Book> bookPages;  //хранит список найденных книг
    private List<Book> topBooks;// хранит полученные ТОП книги (может использоваться наприемр для получения изображений книги)

    private String searchText; // введенный текст для поиска
    private long selectedGenreId; // выбранынй жанр для поиска


    @PostConstruct //после того, как создан ManagedBean, будет автоматически вызываться метод init
    public void init() {
        lazyModel = new LazyDataTable(this);
    } //this это ссылка на текущий контроллер



    public void save() {

        // если было выбрано новое изображение
        if (uploadedImage != null) {
            selectedBook.setImage(uploadedImage);
        }

        // если был выбран новый PDF контент
        if (uploadedContent != null) {
            selectedBook.setContent(uploadedContent);
        }

        bookDao.save(selectedBook);
        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').hide()");// вызов JS из java кода


    }


    // метод автоматически вызывается из LazyDataTable
    @Override
    public Page<Book> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {


        if (sortField == null) { //если не указано поле сортировки
            sortField = "name"; // берем поле "name"
        }

        if (searchType == null){
            bookPages = bookDao.getAll(pageNumber, pageSize, sortField, sortDirection); //отображаем все книги постранично, если не указан тип поиска
        }else {

            switch (searchType) {
                case SEARCH_GENRE:
                    bookPages = bookDao.findByGenre(pageNumber, pageSize, sortField, sortDirection, selectedGenreId);
                    break;
                case SEARCH_TEXT:
                    bookPages = bookDao.search(pageNumber, pageSize, sortField, sortDirection, searchText);
                    break;
                case ALL:
                    bookPages = bookDao.getAll(pageNumber, pageSize, sortField, sortDirection);
                    break;

            }
        }


        return bookPages;
    }


    @Override
    public void addAction() {
        selectedBook = new Book();
        uploadedImage = loadDefaultIcon();
        uploadedContent = null;

        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').show()");
    }

    // загрузить картинку по-умолчанию для обложки
    private byte[] loadDefaultIcon(){
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/no-cover.jpg");
        try {
            return IOUtils.toByteArray(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    // при закрытии диалогового окна - очищать загруженный контент из переменной
    public void onCloseDialog(CloseEvent event) {
        uploadedContent = null;
    }

    @Override
    public void editAction() {
        uploadedImage = selectedBook.getImage();

        // выбранный book уже будет записан в переменную selectedBook (как только пользователь кликнет на редактирование)
        // книга отобразится в диалоговом окне
        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').show()");
    }

    @Override
    public void deleteAction() {
        bookDao.delete(selectedBook);
    }


    // сообщение, сколько данных найдено и по какому критеорию
    public String getSearchMessage(){

        // для доступа к файлам локализации
        ResourceBundle bundle = ResourceBundle.getBundle("library", FacesContext.getCurrentInstance().getViewRoot().getLocale());


        String message=null;

        if (searchType==null){
            return null;
        }
        switch (searchType) {
            case SEARCH_GENRE:
                message = bundle.getString("genre")+ ": '"+genreDao.get(selectedGenreId)+"'";
                break;
            case SEARCH_TEXT:

                if (searchText==null || searchText.trim().length()==0){
                    return null;
                }

                message = bundle.getString("search")+ ": '"+searchText+"'";
                break;
        }

        return message;
    }


    // получить PDF контент книги для чтения
    public byte[] getContent(long id) {

        byte[] content;

        if (uploadedContent != null) {
            content = uploadedContent;
        } else {

            content = bookDao.getContent(id);

        }

        return content;
    }

    // при загрузке обложки - она будет сохраняться в переменную uploadedImage
    public void uploadImage(FileUploadEvent event) {
        if (event.getFile() != null) {
            uploadedImage = event.getFile().getContents();
        }
    }

    // при загрузке PDF контента - он будет сохраняться в переменную uploadedContent
    public void uploadContent(FileUploadEvent event) {
        if (event.getFile() != null) {
            uploadedContent = event.getFile().getContents();
        }
    }


    public List<Book> getTopBooks() {
        topBooks = bookDao.findTopBooks(TOP_BOOKS_LIMIT);
        return topBooks;
    }




    public void showBooksByGenre(long genreId){
        searchType = SearchType.SEARCH_GENRE;
        this.selectedGenreId = genreId;
    }

    public void showAll(){
        searchType = SearchType.ALL;
    }

    public void searchAction(){
        searchType = SearchType.SEARCH_TEXT;
    }

    public Page<Book> getBookPages(){
        return bookPages;
    }

    public void updateViewCount(long viewCount, long id){
        bookDao.updateViewCount(viewCount+1, id);
    }


    // вызывается при голосовании за книгу
    public void onrate(RateEvent rateEvent) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int bookIndex = Integer.parseInt(params.get("bookIndex"));// параметр индекса книги, который передается со страницы

        Book book = bookPages.getContent().get(bookIndex);// по индексу получаем книгу, для которой проголосовали

        // какой рейтинг поставил пользователь
        long currentRating = Long.valueOf(rateEvent.getRating().toString()).longValue();

        // новый рейтинг (суммарный)
        long newRating = book.getTotalRating() + currentRating;

        // сколько проголосовало
        long newVoteCount = book.getTotalVoteCount()+1;

        // среднее значение, которое показывается на странице
        int newAvgRating = calcAverageRating(newRating, newVoteCount);

        bookDao.updateRating(newRating, newVoteCount, newAvgRating, book.getId());

    }


    public int calcAverageRating(long totalRating, long totalVoteCount) {
        if (totalRating == 0 || totalVoteCount == 0) {
            return 0;
        }

        int avgRating = Long.valueOf(totalRating / totalVoteCount).intValue();


        return avgRating;
    }


}
