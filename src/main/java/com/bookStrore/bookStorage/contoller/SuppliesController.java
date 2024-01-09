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
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookStrore.bookStorage.dto.models.SuppliesModelDto;
import com.bookStrore.bookStorage.services.IServiceBase;

@Controller
@RequestMapping("/supplies")
public class SuppliesController implements IController<SuppliesModelDto>
{

    @Autowired
    IServiceBase<SuppliesModelDto> suppliesService;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<SuppliesModelDto> GetEntityById(@PathVariable("id")UUID id) 
    {
        return ResponseEntity.ok(suppliesService.GetEntitieById(id));
    }

    @GetMapping("/")
    @Override
    public ResponseEntity<ArrayList<SuppliesModelDto>> GetAllEntities() 
    {
        return ResponseEntity.ok(suppliesService.GetAllEntities());
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> CreateEntity(SuppliesModelDto model) 
    {
        if(!suppliesService.CreateEntity(model)) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);//если при соохранении ошибка
        return ResponseEntity.ok("Supply model created");
    }

    @PostMapping("/update")
    @Override
    public ResponseEntity<String> UpdateEntity(SuppliesModelDto model) {
        if(!suppliesService.UpdateEntity(model)) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);//если при соохранении ошибка
        return ResponseEntity.ok("Supply model updated");
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> DeleteEntityById(@PathVariable("id")UUID id) {
        if(!suppliesService.DeleteEntityById(id)) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);//если при соохранении ошибка
        return ResponseEntity.ok("Supply model deleted");
    }

}
