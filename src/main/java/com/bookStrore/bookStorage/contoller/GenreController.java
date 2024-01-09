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

@Controller
@RequestMapping("/genre")
public class GenreController implements IController<GenreModelDto>
{
    @Autowired
    IServiceBase<GenreModelDto> genreService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GenreModelDto> GetEntityById(@PathVariable(name = "id")UUID id) 
    {
        return ResponseEntity.ok(genreService.GetEntitieById(id));
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<ArrayList<GenreModelDto>> GetAllEntities() 
    {
        return ResponseEntity.ok(genreService.GetAllEntities());
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<String> CreateEntity(@RequestBody GenreModelDto model) 
    {
        boolean isCreated = genreService.CreateEntity(model);

        if(!isCreated) return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);

        return ResponseEntity.ok("Genre was created successfuly");
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<String> UpdateEntity(GenreModelDto model) 
    {
        boolean isUpdated = genreService.UpdateEntity(model);
        if(!isUpdated) return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
        return ResponseEntity.ok("Genre was updated successfully");
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteEntityById(@PathVariable("id") UUID id) 
    {
        boolean isDeleted = genreService.DeleteEntityById(id);

        if(!isDeleted) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        return ResponseEntity.ok("Genre was successfully deleted");
    }

}
