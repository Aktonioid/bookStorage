package com.bookStrore.bookStorage.dto.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuppliesModelDto 
{
   
    private UUID id; // id поставки

    private ArrayList<SupplyPartModelDto> books;//поставляемые книги        

    private String provider; // поставщик

    private Date expectedDeliveryDate;// дата в которую поставка ожидается
    private Date realDeliveryDate;
    private boolean isArived;

    

    public SuppliesModelDto(UUID id)
    {
        this.id = id;
    }
}
