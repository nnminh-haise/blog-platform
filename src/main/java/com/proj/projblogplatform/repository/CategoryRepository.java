package com.proj.projblogplatform.repository;

import com.proj.projblogplatform.dto.RepositoryResponse;
import com.proj.projblogplatform.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;

@Repository
public class CategoryRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public RepositoryResponse save(Category category) {
        RepositoryResponse response = new RepositoryResponse("sucess", true);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(category);
            transaction.commit();
        }
        catch (Exception e) {
            response.setMessage(e.getMessage()).setStatus(false);
            transaction.rollback();
        }
        finally {
            session.close();
        }

        return response;
    }
}
