package com.bookStrore.bookStorage.dto.mappers;

import com.bookStrore.bookStorage.dto.models.GenreModelDto;
import com.bookStrore.bookStorage.models.GenreModel;

public class GenreModelMapper 
{
    public static GenreModelDto AsDto(GenreModel model)
    {
        GenreModelDto dto = new GenreModelDto(model.getId(), model.getName());
        return dto;
    }    
 
    public static GenreModel AsEntity(GenreModelDto dto)
    {
        GenreModel model = new GenreModel(dto.getId(), dto.getName());
        return model;
    }
}
