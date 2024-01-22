package com.bookStrore.bookStorage.services.supplies;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStrore.bookStorage.dao.sqlDao.SuppliesModelDao;
import com.bookStrore.bookStorage.dto.mappers.SuppliesModelMapper;
import com.bookStrore.bookStorage.dto.models.SuppliesModelDto;
import com.bookStrore.bookStorage.models.SuppliesModel;
import com.bookStrore.bookStorage.services.IServiceBase;

@Service
public class SuppliesService implements IServiceBase<SuppliesModelDto>
{

    @Autowired
    SuppliesModelDao suppliesModelDao;

    @Override
    public ArrayList<SuppliesModelDto> GetAllEntities() 
    {
        ArrayList<SuppliesModelDto> models = null;
        
        models = new ArrayList<SuppliesModelDto>(
        suppliesModelDao.GetAllEntities().stream()
        .map(SuppliesModelMapper::AsDto) //переводим из модели в дто
        .collect(Collectors.toList()));// переводим в list;

        return models;
    }

    @Override
    public SuppliesModelDto GetEntitieById(UUID id) 
    {
        SuppliesModel supplies =  suppliesModelDao.GetEntityById(id);

        if(supplies == null)
        {
            return null; 
        }

        return SuppliesModelMapper.AsDto(supplies);
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

    // метод обрабатывающий ситуацию, когда поставка пришла
    public boolean SupplyArrived(SuppliesModelDto dto)
    {
        if(suppliesModelDao.GetEntityById(dto.getId()) == null)
        {
            return false;
        }
        return suppliesModelDao.SuppliyArrived(SuppliesModelMapper.AsEntity(dto));
    }
    
}
