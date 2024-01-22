package com.bookStrore.bookStorage.dao.sqlDao;

import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.bookStrore.bookStorage.models.BookModel;
import com.bookStrore.bookStorage.models.SuppliesModel;
import com.bookStrore.bookStorage.models.SupplyPartModel;

@Repository
public class SuppliesModelDao extends SQLBaseDao<SuppliesModel>
{   

    public SuppliesModelDao(SessionFactory sessionFactory) {
        super(sessionFactory, SuppliesModel.class);
        this.clazz = SuppliesModel.class;
        this.sessionFactory = sessionFactory;
    }
    SessionFactory sessionFactory;
    Class<SuppliesModel> clazz;    


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

    public boolean SuppliyArrived(SuppliesModel model) // Обработка пришедшей поставки
    {
        Session session = sessionFactory.getCurrentSession(); 
        Transaction transaction = session.getTransaction();
        
        boolean isSuccseed = false; //переменна для заптмт результата записи в бд 

        try
        {
            transaction.begin();
            
            session.merge(model); // обновляем модель поставки в бд

            for (SupplyPartModel book : model.getBooks()) // пробегаемся по всем книгам, что есть в модели поставки и добавляем у их 
            {
                BookModel supplyBook = book.getBook();
                
                
                BookModel dbModel = session.get(BookModel.class, supplyBook.getId());

                if(dbModel == null)
                {
                    continue;
                }

                dbModel.setLeftovers((short)(dbModel.getLeftovers() + supplyBook.getLeftovers()));

                session.merge(dbModel);                
            }
            transaction.commit();
            isSuccseed = true;
        }
        catch(HibernateException e)
        {
            transaction.rollback();
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        return isSuccseed;
    }
    
}
