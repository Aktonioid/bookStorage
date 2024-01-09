package com.bookStrore.bookStorage.services.genre;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStrore.bookStorage.dao.IBaseDao;
import com.bookStrore.bookStorage.dto.mappers.GenreModelMapper;
import com.bookStrore.bookStorage.dto.models.GenreModelDto;
import com.bookStrore.bookStorage.excpetions.OverloadRequiredException;
import com.bookStrore.bookStorage.models.GenreModel;
import com.bookStrore.bookStorage.services.IServiceBase;

@Service
public class GenreService implements IServiceBase<GenreModelDto>
{
    @Autowired
    private IBaseDao<GenreModel> genreModelDao;


    public ArrayList<GenreModelDto> GetAllEntities()
    {
        ArrayList<GenreModel> models = null;
        
        try 
        {
            models = genreModelDao.GetAllEntities();
        } 
        catch (OverloadRequiredException e) 
        {
            e.printStackTrace();
            return null;
        }
        
        return new ArrayList<GenreModelDto>(
            models.stream().map(GenreModelMapper::AsDto).collect(Collectors.toList())
            );
    }

    @Override
    public GenreModelDto GetEntitieById(UUID id) 
    {
        return GenreModelMapper.AsDto(genreModelDao.GetEntityById(id));
    }

    @Override
    public boolean CreateEntity(GenreModelDto model) 
    {
        return genreModelDao.CreateEntity(GenreModelMapper.AsEntity(model));
    }

    @Override
    public boolean UpdateEntity(GenreModelDto upadtableModel) 
    {
        return genreModelDao.UpdateEntity(GenreModelMapper.AsEntity(upadtableModel));
    }

    @Override
    public boolean DeleteEntityById(UUID id) 
    {
        return genreModelDao.DeleteEntityById(id);
    }

}
