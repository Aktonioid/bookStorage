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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookStrore.bookStorage.dto.models.GenreModelDto;
import com.bookStrore.bookStorage.services.IServiceBase;
import com.bookStrore.bookStorage.services.book.BookService;

@Controller
@RequestMapping("/genre")
public class GenreController implements IController<GenreModelDto>
{
    @Autowired
    IServiceBase<GenreModelDto> genreService;
    @Autowired
    BookService bookService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GenreModelDto> GetEntityById(@PathVariable(name = "id")UUID id) 
    {
        GenreModelDto genre = genreService.GetEntitieById(id);

        if(genre == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(genre);
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<ArrayList<GenreModelDto>> GetAllEntities() 
    {
        return ResponseEntity.ok(genreService.GetAllEntities());
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<String> CreateEntity(@RequestBody(required = true) GenreModelDto model) 
    {
        if(model.getName() == null) // если не заполнено поле имени жанра
        {
            return ResponseEntity.badRequest().body("Прислано что-то не то");
        }

        if(!genreService.CreateEntity(model)) return new ResponseEntity<String>(HttpStatus.INSUFFICIENT_STORAGE);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<String> UpdateEntity(@RequestBody(required = true) GenreModelDto model) 
    {
        if(genreService.GetEntitieById(model.getId())  == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        if(!genreService.UpdateEntity(model)) return new ResponseEntity<String>(HttpStatus.INSUFFICIENT_STORAGE);
        return ResponseEntity.ok("Genre was updated successfully");
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteEntityById(@PathVariable("id") UUID id) 
    {

        if(genreService.GetEntitieById(id) == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!bookService.DeleteGenresFromBooks( // удаляем из всех книг с этим жанром его упоминание, чтоб можно было удалять
            bookService.GetBooksByGenre(id), // получаем все книги в которых этот жанр присутствует
             id)) // id удаляемого жанра
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean isDeleted = genreService.DeleteEntityById(id);// удаляем сам жанр
        if(!isDeleted) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        return ResponseEntity.ok("Genre was successfully deleted");
    }

}
