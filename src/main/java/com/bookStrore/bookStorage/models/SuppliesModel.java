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
@Table(name = "supplies")
@Getter
@Setter
@AllArgsConstructor
public class SuppliesModel 
{
    @Id
    private UUID id;

    @OneToMany
    @JoinColumn(name = "supply_id")
    private Set<SupplyPartModel> books;//поставляемые книги        

    private String provider; // поставщик

    @Column(name = "expected_delivery_date")
    @Basic
    @Temporal(TemporalType.DATE)
    private Date expectedDeliveryDate;// дата в которую поставка ожидается

    @Column(name = "is_delivery_arrived")
    private boolean isArived;

    public SuppliesModel(){}

    public SuppliesModel(UUID id)
    {
        this.id = id;
    }
}
