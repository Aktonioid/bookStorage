package com.bookStrore.bookStorage.services.supplies;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.bookStrore.bookStorage.dao.IBaseDao;
import com.bookStrore.bookStorage.dto.mappers.SuppliesModelMapper;
import com.bookStrore.bookStorage.dto.models.SuppliesModelDto;
import com.bookStrore.bookStorage.excpetions.OverloadRequiredException;
import com.bookStrore.bookStorage.models.SuppliesModel;
import com.bookStrore.bookStorage.services.IServiceBase;

public class SuppliesService implements IServiceBase<SuppliesModelDto>
{

    @Autowired
    IBaseDao<SuppliesModel> suppliesModelDao;

    @Override
    public ArrayList<SuppliesModelDto> GetAllEntities() 
    {
        ArrayList<SuppliesModelDto> models = null;
        
        try 
        {
            models = new ArrayList<SuppliesModelDto>(
            suppliesModelDao.GetAllEntities().stream()
            .map(SuppliesModelMapper::AsDto) //переводим из модели в дто
            .collect(Collectors.toList()));// переводим в list;
        } 
        catch (OverloadRequiredException e) 
        {
            e.printStackTrace();
            return null;    
        }

        return models;
    }

    @Override
    public SuppliesModelDto GetEntitieById(UUID id) 
    {
        return SuppliesModelMapper.AsDto(suppliesModelDao.GetEntityById(id));
    }

    @Override
    public boolean CreateEntity(SuppliesModelDto model) 
    {
        return suppliesModelDao.CreateEntity(SuppliesModelMapper.AsEntity(model));
    }

    @Override
    public boolean UpdateEntity(SuppliesModelDto upadtableModel) 
    {
        return suppliesModelDao.UpdateEntity(SuppliesModelMapper.AsEntity(upadtableModel));
    }

    @Override
    public boolean DeleteEntityById(UUID id) 
    {
        return suppliesModelDao.DeleteEntityById(id);
    }
    
}
