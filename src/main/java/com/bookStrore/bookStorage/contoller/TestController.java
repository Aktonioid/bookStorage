package com.bookStrore.bookStorage.contoller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bookStrore.bookStorage.dao.sqlDao.BookModelDao;
import com.bookStrore.bookStorage.models.BookModel;
import com.bookStrore.bookStorage.models.GenreModel;

@Controller
@RequestMapping("/test")
public class TestController 
{

    @Autowired
    BookModelDao dao;
    
    @PostMapping("/post")
    public ResponseEntity<Boolean> IsExhists(@RequestBody BookModel model)
    {
        return ResponseEntity.ok(false);
    }

    @GetMapping("/{genre_id}")
    public ResponseEntity<ArrayList<BookModel>> Test(@PathVariable("genre_id") UUID id)
    {
        GenreModel genre = new GenreModel(id);
        return ResponseEntity.ok(dao.GetBooksByGenre(genre));
    }
}
