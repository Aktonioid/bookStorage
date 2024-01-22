package com.bookStrore.bookStorage.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "genres")
@Getter
@Setter
public class GenreModel 
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "genre_id")
    private UUID id;
    private String name;
    
    public GenreModel(){}

    public GenreModel(UUID id, String name)
    {
        this.name = name;
        this.id = id;
    }
    public GenreModel(UUID id)
    {
        this.id = id;
    }
}
