package com.bookStrore.bookStorage.dto.mappers;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.bookStrore.bookStorage.dto.models.OrderModelDto;
import com.bookStrore.bookStorage.dto.models.OrderPartModelDto;
import com.bookStrore.bookStorage.models.OrderModel;

public class OrderModelMapper 
{
    public static OrderModel AsEntity(OrderModelDto dto)
    {
        return new OrderModel(dto.getId(),
                            dto.getBooks().stream().map(OrderPartModelMapper::AsEntity).collect(Collectors.toSet()),
                            dto.isSent(),
                            dto.getSendDate(),
                            dto.getUserFullName(),
                            dto.getDeliveryAdress(),
                            dto.isPaymentStatus(),
                            dto.getUserId());
    }

    public static OrderModelDto AsDto(OrderModel model)
    {
        return new OrderModelDto(model.getId(),
                                new ArrayList<OrderPartModelDto>(
                                    model.getBooks().stream().map(OrderPartModelMapper::AsDto).collect(Collectors.toList())),
                                model.isSent(),
                                model.getSendDate(),
                                model.getUserFullName(),
                                model.getDeliveryAdress(),
                                model.getUserId(),
                                model.isPaymentStatus());
    }
}
