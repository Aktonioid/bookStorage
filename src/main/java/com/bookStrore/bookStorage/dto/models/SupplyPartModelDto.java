package com.bookStrore.bookStorage.dto.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplyPartModelDto 
{
    private UUID id; // id
    private BookModelDto book; // модель поставляемой книги
    private UUID supplyId; // id содели поставки по которой будут получаться данные из бд
    private int bookCount; //Сколько книг в поставке 
}
