package com.bookStrore.bookStorage.services.order;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStrore.bookStorage.dao.sqlDao.OrderModelDao;
import com.bookStrore.bookStorage.dto.mappers.OrderModelMapper;
import com.bookStrore.bookStorage.dto.models.OrderModelDto;
import com.bookStrore.bookStorage.models.OrderModel;
import com.bookStrore.bookStorage.services.IServiceBase;

@Service
public class OrderService implements IServiceBase<OrderModelDto>
{

    @Autowired
    OrderModelDao dao;

    @Override
    public ArrayList<OrderModelDto> GetAllEntities() 
    {
        return new ArrayList<OrderModelDto>(dao.GetAllEntities().stream()
                                                .map(OrderModelMapper::AsDto).collect(Collectors.toList()));    
    }

    @Override
    public OrderModelDto GetEntitieById(UUID id) 
    {
        OrderModel model = dao.GetEntityById(id);

        if(model == null) return null;

        return OrderModelMapper.AsDto(model);        
    }

    @Override
    public boolean CreateEntity(OrderModelDto model) 
    {
        return dao.CreateEntity(OrderModelMapper.AsEntity(model));
    }

    @Override
    public boolean UpdateEntity(OrderModelDto upadtableModel) 
    {
        return dao.UpdateEntity(OrderModelMapper.AsEntity(upadtableModel));
    }

    @Override
    public boolean DeleteEntityById(UUID id) 
    {
        return dao.DeleteEntityById(id);
    }
    
}
