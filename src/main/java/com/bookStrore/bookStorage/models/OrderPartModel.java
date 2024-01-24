package com.bookStrore.bookStorage.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_part")
@Getter
@Setter
@AllArgsConstructor
public class OrderPartModel 
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookModel book;// отправляемая книга

    @Column(name = "book_count")
    private int bookCount; //Сколько этой книги отправляем
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderModel orderId; // к какому заказу имеет отношение

    public OrderPartModel(){}
}
