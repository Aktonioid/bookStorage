package com.bookStrore.bookStorage.dto.models;

import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenreModelDto 
{
    private UUID id;
    private String name;
    
    // public GenreModelDto(UUID id, String name)
    // {
    //     this.id = id;
    //     this.name =name;
    // }
}
