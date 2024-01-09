package com.bookStrore.bookStorage.dto.mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.bookStrore.bookStorage.dto.models.SuppliesModelDto;
import com.bookStrore.bookStorage.dto.models.SupplyPartModelDto;
import com.bookStrore.bookStorage.models.SuppliesModel;
import com.bookStrore.bookStorage.models.SupplyPartModel;

public class SuppliesModelMapper 
{
    public static SuppliesModelDto AsDto(SuppliesModel model)
    {
        
        return new SuppliesModelDto(model.getId(), // присваиваем id поставки
        new ArrayList<SupplyPartModelDto>(model.getBooks().stream().map(SupplyPartModelMapper::AsDto).collect(Collectors.toList())), 
            // строчкой выше переводим модель части поставки в дто модели поставки
            model.getProvider(), // присваиваем дтошке имя поставщика из модели
            model.getExpectedDeliveryDate(), // дата поставки
            model.isArived()); // поступила ли доставки или нет
    }
    
    public static SuppliesModel AsEntity(SuppliesModelDto dto)
    {
        return new SuppliesModel(dto.getId(), // перенос id
            new HashSet<SupplyPartModel>(dto.getBooks().stream().map(SupplyPartModelMapper::AsEntity).collect(Collectors.toList())),
            // строчкой выше переводим модель части поставки из дто в модель части поставки
            dto.getProvider(), // вставляем провайдера
            dto.getExpectedDeliveryDate(), // дата поставки
            dto.isArived()); // поступила ли поставка
    }
}
