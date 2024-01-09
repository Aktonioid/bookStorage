package com.bookStrore.bookStorage.dao.sqlDao;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookStrore.bookStorage.models.GenreModel;

@Repository
public class GenreModelDao extends BaseDao<GenreModel>
{
    @Autowired
    public GenreModelDao(SessionFactory sessionFactory)
    {   
        super(sessionFactory, GenreModel.class);
    }
    @Autowired
    SessionFactory sessionFactory;
    Class<GenreModel> clazz = GenreModel.class;
    

    @Override
    public ArrayList<GenreModel> GetAllEntities()
    {
        String hql = "SELECT g FROM GenreModel g";
        
        Session session = sessionFactory.getCurrentSession();

        return new ArrayList<GenreModel>(session.createQuery(hql, clazz).getResultList()); // получаем все жанры
    }

}
