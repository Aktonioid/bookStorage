package com.bookStrore.bookStorage.contoller;

import java.util.ArrayList;
import java.util.Date;
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

import com.bookStrore.bookStorage.dto.models.OrderModelDto;
import com.bookStrore.bookStorage.services.office.ExcelService;
import com.bookStrore.bookStorage.services.order.OrderService;

@Controller
@RequestMapping("orders")
public class OrderController implements IController<OrderModelDto>
{

    @Autowired
    ExcelService excelService;
    @Autowired
    OrderService orderService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OrderModelDto> GetEntityById(@PathVariable(name = "id") UUID id) 
    {
        OrderModelDto dto = orderService.GetEntitieById(id);

        // Если модели нет, то кидаем 404
        if(dto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // если все успешно 200
        return ResponseEntity.ok(dto);
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<ArrayList<OrderModelDto>> GetAllEntities() 
    {
        return ResponseEntity.ok(orderService.GetAllEntities());
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<String> CreateEntity(@RequestBody OrderModelDto model) 
    {
        if(model.getBooks()==null||
        model.getDeliveryAdress()==null||
        model.getUserFullName()==null||
        model.getUserId()==null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean isCreated = orderService.CreateEntity(model);
        
        if(!isCreated)
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @PutMapping()
    public ResponseEntity<String> UpdateEntity(@RequestBody OrderModelDto model) {
        if(
            model.getId() ==null||
            model.getBooks()==null||
            model.getDeliveryAdress()==null||
            model.getUserFullName()==null||
            model.getUserId()==null
            )
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(orderService.GetEntitieById(model.getId()) == null)
        { 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean isUpdated = orderService.UpdateEntity(model);

        if(!isUpdated)
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteEntityById(@PathVariable(name = "id") UUID id) 
    {
        if(orderService.GetEntitieById(id) == null)
        { 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean isDeleted = orderService.DeleteEntityById(id);

        if(!isDeleted)
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/send")
    public ResponseEntity<String> OrderSended(UUID id)
    {
        OrderModelDto order = orderService.GetEntitieById(id);

        if(order == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } 

        order.setSendDate(new Date());
        order.setSent(true);

        if(!orderService.UpdateEntity(order))
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return ResponseEntity.ok(excelService.OrderExcelEstimate(order));
    }
    
}
