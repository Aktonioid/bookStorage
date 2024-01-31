package com.bookStrore.bookStorage.models;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
public class OrderModel 
{
    @Id
    private UUID id;

    @OneToMany
    @JoinColumn(name = "order_id")
    private Set<OrderPartModel> books;
    
    @Column(name = "is_sent")
    private boolean isSent;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name="send_date")
    Date sendDate;

    @Column(name = "user_full_name")
    private String userFullName;// делаю так а не join, так как дальше петом будет
                        // типа магазин по продаже книг и могут возникнуть проблемы
                        // с моделями закаов, а так по идее не будет проблем

    @Column(name = "delivery_adress")
    private String deliveryAdress;// адрес доставки\

    @Column(name = "payment_status")
    private boolean paymentStatus; // Оплачен ли заказ

    @Column(name = "user_id")
    private UUID userId; // id пользователя, чтоб потом было удобно искать заказы по магазину

    public OrderModel(){}

    public OrderModel(UUID id)
    {
        this.id = id;
    }
}
