package com.bookStrore.bookStorage.dto.mappers;

import com.bookStrore.bookStorage.dto.models.SupplyPartModelDto;
import com.bookStrore.bookStorage.models.SuppliesModel;
import com.bookStrore.bookStorage.models.SupplyPartModel;

public class SupplyPartModelMapper 
{
    public static SupplyPartModelDto AsDto(SupplyPartModel model)
    {
        return new SupplyPartModelDto(model.getId(), // Получение модели часиь поставки
                                    BookModelMapper.AsDto(model.getBook()), 
                                    model.getSupplyId().getId(), 
                                    model.getBookCount());
    }    

    public static SupplyPartModel AsEntity(SupplyPartModelDto dto)
    {
        return new SupplyPartModel(dto.getId(),
                                    BookModelMapper.AsEntity(dto.getBook()), 
                                        new SuppliesModel(dto.getSupplyId()),
                                    dto.getBookCount());
    }
}
