package com.bookStrore.bookStorage.dao.sqlDao;

import java.util.ArrayList;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.bookStrore.bookStorage.models.SuppliesModel;
import com.bookStrore.bookStorage.models.SupplyPartModel;

@Repository
public class SuppliesModelDao extends BaseDao<SuppliesModel>
{   

    public SuppliesModelDao(SessionFactory sessionFactory) {
        super(sessionFactory, SuppliesModel.class);
        this.clazz = SuppliesModel.class;
        this.sessionFactory = sessionFactory;
    }
    SessionFactory sessionFactory;
    Class<SuppliesModel> clazz;    

    @Override
    public ArrayList<SuppliesModel> GetAllEntities()
    {
        String hql = "SELECT s FROM SuppliesModel s"; //query для получения всех данных из SuppliesModel
        
        Session session = sessionFactory.getCurrentSession();
        
        return new ArrayList<SuppliesModel>(session.createQuery(hql, clazz).getResultList()); // получаем все SuppliesModel
    }

    @Override
    public boolean CreateEntity(SuppliesModel entity) 
    {

        entity.setId(UUID.randomUUID()); // генерируем id модели поставки

        SuppliesModel idForSupplyPart = new SuppliesModel(entity.getId()); //создаём экземпляр модели поставок только с id для сохранения
                                                                           //части поставки в бд. Надо для референса на id

        for (SupplyPartModel part : entity.getBooks()) // пробегаемся по всем частям поставок и добавляем id посвок
        {
            part.setSupplyId(idForSupplyPart); 
        }

        boolean isCreated = false;

        Session session= sessionFactory.getCurrentSession();
        
        Transaction transaction = session.getTransaction();

        try
        {
            transaction.begin();
            session.persist(entity); // сохраняем модель поставки
            session.flush();

            for (SupplyPartModel part : entity.getBooks()) 
            {
                session.persist(part); // сохраняем в бд данные о частях поставки
            }
            
            transaction.commit(); // сохранение данных в бд
            
            isCreated = true;
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback();
        }
        catch(Exception e) // дополнительный catch от всех ошибок, не только hibernate чтоб 
                           // если что в бд не осталось лежать только часть данных
        {
            transaction.rollback(); //откат сохранеенных данных в бд при ошибке
            throw e;
        }

        return isCreated;
    }

    
}
