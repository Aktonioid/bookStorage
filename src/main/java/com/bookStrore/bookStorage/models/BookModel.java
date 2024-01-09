package com.bookStrore.bookStorage.models;

import java.util.Set;
import java.util.UUID;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;   
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BookModel 
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "book_name")
    private String bookName; //Название книги

    @Column(name = "author_name")
    private String authorName; // автор
    private String publisher; //издательство
    private int price; // стоимость
    private String isbn; //Уникальный идентификатор книги
    
    @OneToMany
    @Nullable
    private Set<GenreModel> genres; //Жанры книги
    
    private String description; // описание книги
    @Column(name = "picture_url")
    private String pictureUrl; // ссылка на url книги
    private short leftovers; // остатки на складе 

}
