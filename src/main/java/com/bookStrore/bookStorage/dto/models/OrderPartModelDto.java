package com.bookStrore.bookStorage.dto.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderPartModelDto 
{
    private UUID id; // id части
    private BookModelDto book;// отправляемая книга
    private int bookCount; // сколько ниги отправляем
    private UUID orderId; // Id заказа к которому книга относится
    
    public OrderPartModelDto(){}
}
