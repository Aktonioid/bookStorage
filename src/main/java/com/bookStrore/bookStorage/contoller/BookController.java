package com.bookStrore.bookStorage.contoller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.bookStrore.bookStorage.dto.models.BookModelDto;
import com.bookStrore.bookStorage.services.book.BookService;

@Controller
@RequestMapping("/book")
// так-то этот контроллер тоже должен наследоваться от интерфейсса IController, но так как он один получает на вход различне значения
// то под него одного гордить новый интерфейс нет смысла
public class BookController
{
    // тут тож по идее, как и в других контроллерах должен быть вызов именно реализации интерфейса, но так как он один работает с файлами
    // не получается так сделать
    @Autowired
    BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookModelDto> GetEntityById(@PathVariable("id")UUID id) 
    {
        BookModelDto dto = bookService.GetEntitieById(id); //результат получениый при поиске по id

        if(dto == null) // елси книги с таким id нет, то кидаем 404
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/")
    public ResponseEntity<ArrayList<BookModelDto>> GetAllEntities() 
    {
        return ResponseEntity.ok(bookService.GetAllEntities()); // получаем все сущности из типа книга из бд
    }

    @PostMapping("/create")
    public ResponseEntity<String> CreateEntity(@RequestPart(name = "book", required = true) BookModelDto model,
                                             @RequestPart(name = "cover", required = false) MultipartFile file) 
    {
        // проверка на то имеются ли в книге самые необходимые параметры
        if(model.getAuthorName() == null || // Есть ли автор
        model.getIsbn() == null|| // есть ли isbn
        model.getPrice() == 0|| // етсь ли цена
        model.getBookName() == null // есть ли название книги
        )
        {
            return ResponseEntity.badRequest().body("Не полное тело запроса");
        }

        // если файл пришел, то мы сохраняем его и запихиваем его урл в сохраняемую модель
        if(file != null)
        {
            String path = bookService.SaveBookCover(file); //путь куда сохранена книга
            model.setPictureUrl(path); // ставим этот путь в книгу
        }

        if(!bookService.CreateEntity(model)) return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);// если при сохранении ошибка

        return new ResponseEntity<>(HttpStatus.CREATED);
        // return ResponseEntity.ok("Book model created");
    }

    @PutMapping("/update")
    public ResponseEntity<String> UpdateEntity(@RequestPart(name = "book", required = true) BookModelDto model,
                                             @RequestPart(name = "cover", required = false) MultipartFile file) 
    {
        // если файл пришел, то мы сохраняем его и запихиваем его урл в сохраняемую модель
        if(file != null)
        {
            String path = bookService.SaveBookCover(file);// путь куда сохраняется обложка книги
            model.setPictureUrl(path); // устанавливаем этот путь в обновляемую модель
        }

        if(bookService.GetEntitieById(model.getId()) == null) // проверка на то что такая модель существует в бд 
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Если нет, то кидаем ответ не найдено
        }

        if(!bookService.UpdateEntity(model)) return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);//если при соохранении ошибка
        
        return ResponseEntity.ok("Book model updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteEntityById(@PathVariable("id") UUID id) 
    {
        if(bookService.GetEntitieById(id) == null) // проверка на сущестрование такой записи
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // такой записи в бд нет
        }

        if(!bookService.DeleteEntityById(id)) return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);//если при соохранении ошибка
        return ResponseEntity.ok("Book model deleted");
    }

    @GetMapping("/genre/{genre_id}")
    public ResponseEntity<ArrayList<BookModelDto>> GetBookByGenreId(@PathVariable("genre_id") UUID id)
    {
        return ResponseEntity.ok(bookService.GetBooksByGenre(id));
    }
}
