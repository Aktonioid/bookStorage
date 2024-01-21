package com.bookStrore.bookStorage.dao.sqlDao;

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
    
}
