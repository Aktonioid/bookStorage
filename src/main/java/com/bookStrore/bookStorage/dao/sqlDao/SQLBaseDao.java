package com.bookStrore.bookStorage.dao.sqlDao;

import java.util.ArrayList;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import com.bookStrore.bookStorage.dao.IBaseDao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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
    public ArrayList<T> GetAllEntities()
    {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> root = cq.from(clazz);

        cq.select(root); // получаем все записи из бд
        Query<T> query = session.createQuery(cq);
        
        return new ArrayList<T>(query.list());
    }

    @Override
    public T GetEntityById(UUID id) // получаем модель по id
    {

        Session session = sessionFactory.getCurrentSession();
     
        // создание запроса в бд
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> root = cq.from(clazz);

        cq.select(root).where(root.get("id").in(id)); // ишем по id в бд
        Query<T> query = session.createQuery(cq); 

        T entity = query.uniqueResult();

        return entity;
    }

    @Override
    public boolean CreateEntity(T entity) // создание моделей
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();

        boolean isCreated = false;// переменная необходимая для записи состояния сохранния данных в бд

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
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<T> cd = cb.createCriteriaDelete(clazz); //запрос для удаления
            Root<T> root = cd.from(clazz);

            cd.where(cb.equal(root.get("id"), id));// удалить запись, в которой id равняется заданному

            transaction.begin();   

            MutationQuery query = session.createMutationQuery(cd); // создаем запрос в бд для того чтобы удалить записть по id 
            
            query.executeUpdate(); // Посылаем запрос на удаление

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
