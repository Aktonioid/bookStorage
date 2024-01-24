package com.bookStrore.bookStorage.dao.sqlDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookStrore.bookStorage.models.OrderModel;

// тут по факту нк должно быть возсожности создавать или удалять заказы, но на данный момент будет
// для того чтобы точно можно было нувидеть полный фунукционал
@Repository
public class OrderModelDao extends SQLBaseDao<OrderModel>
{
    @Autowired
    public OrderModelDao(SessionFactory sessionFactory) 
    {
        super(sessionFactory, OrderModel.class);
        this.sessionFactory = sessionFactory;
    }
    SessionFactory sessionFactory;
    

}
