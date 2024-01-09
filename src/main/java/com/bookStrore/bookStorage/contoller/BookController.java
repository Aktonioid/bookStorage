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
        return ResponseEntity.ok(bookService.GetEntitieById(id));
    }

    @GetMapping("/")
    public ResponseEntity<ArrayList<BookModelDto>> GetAllEntities() 
    {
        return ResponseEntity.ok(bookService.GetAllEntities());
    }

    @PostMapping("/create")
    public ResponseEntity<String> CreateEntity(@RequestPart("book") BookModelDto model,
                                             @RequestPart(name = "file", required = false) MultipartFile file) 
    {
        // если файл пришел, то мы сохраняем его и запихиваем его урл в сохраняемую модель
        if(!file.isEmpty())
        {
            model.setPictureUrl(bookService.SaveBookCover(file));
        }

        if(!bookService.CreateEntity(model)) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);// если при сохранении ошибка

        return ResponseEntity.ok("Book model created");
    }

    @PutMapping("/update")
    public ResponseEntity<String> UpdateEntity(@RequestPart("book") BookModelDto model,
                                             @RequestPart(name = "file", required = false) MultipartFile file) 
    {
        // если файл пришел, то мы сохраняем его и запихиваем его урл в сохраняемую модель
        if(!file.isEmpty())
        {
            model.setPictureUrl(bookService.SaveBookCover(file));
        }

        if(!bookService.UpdateEntity(model)) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);//если при соохранении ошибка
        
        return ResponseEntity.ok("Book model updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteEntityById(@PathVariable("id") UUID id) 
    {
        if(!bookService.DeleteEntityById(id)) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);//если при соохранении ошибка
        return ResponseEntity.ok("Book model deleted");
    }


}
