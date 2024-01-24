package com.bookStrore.bookStorage.dto.mappers;

import com.bookStrore.bookStorage.dto.models.GenreModelDto;
import com.bookStrore.bookStorage.models.GenreModel;

public class GenreModelMapper 
{
    public static GenreModelDto AsDto(GenreModel model)
    {
        return new GenreModelDto(model.getId(), model.getName());
    }    
 
    public static GenreModel AsEntity(GenreModelDto dto)
    {
        return new GenreModel(dto.getId(), dto.getName());
    }
}
