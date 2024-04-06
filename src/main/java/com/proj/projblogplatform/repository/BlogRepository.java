package com.proj.projblogplatform.repository;

import com.proj.projblogplatform.model.Blog;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BlogRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Blog> findAll() {
        Session session = sessionFactory.getCurrentSession();
        String findAllBlog = "FROM Blog";
        Query query = session.createQuery(findAllBlog);
        List<Blog> blogs = query.list();

        return blogs;
    }
}
