package com.bookStrore.bookStorage.dto.mappers;

import com.bookStrore.bookStorage.dto.models.OrderPartModelDto;
import com.bookStrore.bookStorage.models.OrderModel;
import com.bookStrore.bookStorage.models.OrderPartModel;

public class OrderPartModelMapper 
{
    public static OrderPartModel AsEntity(OrderPartModelDto dto)
    {
        return new OrderPartModel(dto.getId(),// id части заказа
                                 BookModelMapper.AsEntity(dto.getBook()),//превод книги в dto
                                 dto.getBookCount(),//получаем колличество книг
                                 new OrderModel(dto.getOrderId()));//Заказ только с id
    } 

    public static OrderPartModelDto AsDto(OrderPartModel model)
    {
        return new OrderPartModelDto(model.getId(),// id части заказа
                                 BookModelMapper.AsDto(model.getBook()),//превод книги в dto
                                 model.getBookCount(),//получаем колличество книг
                                 model.getOrderId().getId());//Заказ только с id
    } 
}
