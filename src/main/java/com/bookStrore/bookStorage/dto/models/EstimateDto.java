package com.bookStrore.bookStorage.dto.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//класс в котороый преобразуются модели поставки и отправки для создания сметы
@Getter
@Setter
@AllArgsConstructor
public class EstimateDto 
{
    private BookModelDto book;
    private int bookCount;

    public EstimateDto(){}
}
