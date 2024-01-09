package com.bookStrore.bookStorage.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "supply_delivery")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplyPartModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // тут связь в формате many to one, потому что supplyPartModel 
    //используется в сете и в разных заказах могут быть одинаковые книги
    // и подтягиваться модель книги может не единожды
    @ManyToOne 
    @JoinColumn(name = "book_id")
    private BookModel book;
    
    @ManyToOne
    @JoinColumn(name = "supply_id", nullable = false)
    @JsonIgnore
    private SuppliesModel supplyId;
    private short bookCount; //Сколько книг в поставке 
}
