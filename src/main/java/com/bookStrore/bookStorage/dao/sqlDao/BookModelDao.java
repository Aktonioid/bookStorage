package com.bookStrore.bookStorage.dao.sqlDao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookStrore.bookStorage.models.BookModel;
import com.bookStrore.bookStorage.models.GenreModel;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class BookModelDao extends BaseDao<BookModel>
{
    @Autowired
    public BookModelDao(SessionFactory sessionFactory) {
        super(sessionFactory, BookModel.class);
        this.sessionFactory = sessionFactory;
    }


    @Autowired
    SessionFactory sessionFactory;
    Class<BookModel> clazz = BookModel.class;
    

    // Получаем все книги по жанру, мб нескольким если надо
    public ArrayList<BookModel> GetBooksByGenre(GenreModel genre)
    {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<BookModel> cq = cb.createQuery(clazz);
        Root<BookModel> root = cq.from(clazz);

        cq.where(root.get("genres"));

        String hql = "SELECT b FROM BookModel b JOIN b.genres g where g.id = :gid";

        Query<BookModel> query  = session.createQuery(hql,clazz);
        query.setParameter("gid", genre.getId()); // установка id жанра по которому надо произвести поиск

        ArrayList<BookModel> models = new ArrayList<BookModel>(query.getResultList());
        return models;
    }

    public boolean DeleteGenresFromBooks(ArrayList<BookModel> models)
    {
        boolean isUpdated = false; // для записи ответа о том сохранилось ли или нет

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();

        try
        {
            transaction.begin();
            for (BookModel book : models) 
            {
                session.merge(book); // обновляем книги
            }
            
            transaction.commit();
            isUpdated = true;
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
            transaction.rollback();
            isUpdated = false; // бля на всякий
            return isUpdated;
        }

        return isUpdated;
    }

}
