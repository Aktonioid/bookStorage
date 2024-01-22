package com.bookStrore.bookStorage.contoller;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.apache.catalina.connector.Response;
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

import com.bookStrore.bookStorage.dto.models.SuppliesModelDto;
import com.bookStrore.bookStorage.services.supplies.SuppliesService;

@Controller
@RequestMapping("/supplies")
public class SuppliesController implements IController<SuppliesModelDto>
{

    @Autowired
    SuppliesService suppliesService;

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
    public ResponseEntity<String> CreateEntity(@RequestBody(required = true)SuppliesModelDto model) 
    {
        if(model.getBooks()== null ||
        model.getProvider() == null||
        model.getExpectedDeliveryDate() == null
        )
        {
            return ResponseEntity.badRequest().body("Body is incorrect");
        }
        if(!suppliesService.CreateEntity(model)) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);//если при соохранении ошибка
        return ResponseEntity.ok("Supply model created");
    }

    @PostMapping("/update")
    @Override
    public ResponseEntity<String> UpdateEntity(@RequestBody(required = true)SuppliesModelDto model) 
    {
        // проверка на то все ли верно прислано
        if(model.getBooks()== null ||
        model.getProvider() == null||
        model.getExpectedDeliveryDate() == null
        )
        {
            return ResponseEntity.badRequest().body("Body is incorrect");
        }

        // проврека на то существует ли объект
        if(suppliesService.GetEntitieById(model.getId()) == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!suppliesService.UpdateEntity(model)) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);//если при соохранении ошибка
        return ResponseEntity.ok("Supply model updated");
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> DeleteEntityById(@PathVariable("id")UUID id) 
    {
        // проврека на то что такой объект существует
        if(suppliesService.GetEntitieById(id) == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!suppliesService.DeleteEntityById(id)) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);//если при соохранении ошибка
        return ResponseEntity.ok("Supply model deleted");
    }

    @PutMapping("/arrived")
    // Поставка пришла 
    public ResponseEntity<String> SupplyArrived(@RequestBody SuppliesModelDto dto)
    {
        
        if(suppliesService.GetEntitieById(dto.getId()) == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(dto.getExpectedDeliveryDate().getTime() > new Date().getTime())
        {
            return ResponseEntity.badRequest().body("Delivery date is greater than current one");
        }

        if(dto.isArived())
        {
            return ResponseEntity.badRequest().body("Delivery already delivered");
        }

        boolean isDelivered = suppliesService.SupplyArrived(dto); // проверка на то сохранилась ли модель поставки и книг

        if(!isDelivered) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return ResponseEntity.ok("Successuly delivered");
    }

}
