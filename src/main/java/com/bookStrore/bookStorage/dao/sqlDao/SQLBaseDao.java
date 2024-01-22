package com.bookStrore.bookStorage.dao.sqlDao;

import java.util.ArrayList;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.bookStrore.bookStorage.dao.IBaseDao;
import com.bookStrore.bookStorage.excpetions.OverloadRequiredException;

public class SQLBaseDao<T> implements IBaseDao<T>
{
    SessionFactory sessionFactory;
    Class<T> clazz;

    public SQLBaseDao(SessionFactory sessionFactory, Class<T> clazz)
    {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public ArrayList<T> GetAllEntities() throws OverloadRequiredException
    {
        throw new OverloadRequiredException("GetAllEntities");
    }

    @Override
    public T GetEntityById(UUID id) // получаем модель по id
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        T entity = session.get(clazz, id);

        transaction.commit();
        return entity;
    }

    @Override
    public boolean CreateEntity(T entity) // создание моделей
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        boolean isCreated = false;

        try
        {
            transaction.begin();
            session.persist(entity); // сохранение модели в бд
            transaction.commit(); // отправка данных в бд и сохрание её состояния 
            isCreated = true;
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback(); // откакт состояния бд
        }
        catch(Exception e) // дополнительный catch от всех ошибок, не только hibernate чтоб 
                           // если что в бд не осталось лежать только часть данных
        {
            e.printStackTrace();
            transaction.rollback();
        }
        finally
        {
            session.close();
        }

        return isCreated;
    }

    @Override
    public boolean UpdateEntity(T entity)  // обновление даннах в бд
    {
        boolean isUpdated = false;
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();

        try
        {
            transaction.begin();
            session.merge(entity); //обновяем модель в бд
            transaction.commit();
            isUpdated = true;
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback();
        }
        catch(Exception e) // дополнительный catch от всех ошибок, не только hibernate чтоб 
                           // если что в бд не осталось лежать только часть данных
        {
            e.printStackTrace();
            transaction.rollback();
        }

        return isUpdated;
    }

    @Override
    public boolean DeleteEntityById(UUID id) // удаление модели по id
    {
        boolean isDeleted = false;

        Session session= sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        
        try
        {
            Object deletableModel;
            transaction.begin();   
            //по идее можно сделать так, чтоб сразу приходила модель, которую наду удалить
            deletableModel = session.get(clazz, id);// находим в бд модель
            session.remove(deletableModel);// удаляем из бд модель
            transaction.commit();
            isDeleted = true;
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback();
        }
        catch(Exception e) // дополнительный catch от всех ошибок, не только hibernate чтоб 
                           // если что в бд не осталось лежать только часть данных
        {
            e.printStackTrace();
            transaction.rollback();
        }
        
        return isDeleted;
    }
    
}
