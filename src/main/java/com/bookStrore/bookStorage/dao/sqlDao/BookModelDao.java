package com.bookStrore.bookStorage.dao.sqlDao;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookStrore.bookStorage.excpetions.OverloadRequiredException;
import com.bookStrore.bookStorage.models.BookModel;

@Repository
public class BookModelDao extends SQLBaseDao<BookModel>
{
    @Autowired
    public BookModelDao(SessionFactory sessionFactory) {
        super(sessionFactory, BookModel.class);
    }


    @Autowired
    SessionFactory sessionFactory;
    Class<BookModel> clazz = BookModel.class;
    

    @Override
    public ArrayList<BookModel> GetAllEntities() throws OverloadRequiredException {
        Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT b FROM BookModel b";

        return new ArrayList<BookModel>(session.createQuery(hql, clazz).getResultList());
    }

        
    
}
