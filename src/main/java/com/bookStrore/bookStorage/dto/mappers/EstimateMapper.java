package com.bookStrore.bookStorage.dto.mappers;

import com.bookStrore.bookStorage.dto.models.EstimateDto;
import com.bookStrore.bookStorage.dto.models.OrderPartModelDto;
import com.bookStrore.bookStorage.dto.models.SupplyPartModelDto;

public class EstimateMapper 
{
    public static EstimateDto AsEstimate(SupplyPartModelDto dto)
    {
        return new EstimateDto(dto.getBook(), dto.getBookCount());
    }

    public static EstimateDto AsEstimate(OrderPartModelDto dto)
    {
        return new EstimateDto(dto.getBook(), dto.getBookCount());
    }
}
