package com.bookStrore.bookStorage.dto.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderModelDto 
{
    private UUID id;
    private ArrayList<OrderPartModelDto> books;
    private boolean isSent;
    private Date sendDate;
    private String userFullName;
    private String deliveryAdress;
    private UUID userId;


    public OrderModelDto(){}

    public OrderModelDto(UUID id)
    {
        this.id = id;
    }
}
