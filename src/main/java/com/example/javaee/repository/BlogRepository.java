package com.example.javaee.repository;

import com.example.javaee.dto.ErrorResponse;
import com.example.javaee.exceptions.ResourceNotFoundException;
import com.example.javaee.model.Blog;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.core.NestedExceptionUtils.getRootCause;

@Repository
public class BlogRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Blog> findAll() {
        Session session = sessionFactory.getCurrentSession();
        String findAllBlogQuery = "SELECT b FROM Blog AS b WHERE b.deleteAt IS NULL";
        Query<Blog> query = session.createQuery(findAllBlogQuery, Blog.class);
        List<Blog> blogs = query.list();

        if (blogs.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any blog!");
        }
        return blogs;
    }

    @Transactional
    public Optional<Blog> findById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        String findByIdQuery = "SELECT b FROM Blog AS b WHERE b.deleteAt IS NULL AND b.id = :id";
        Query<Blog> query = session.createQuery(findByIdQuery, Blog.class);
        query.setParameter("id", id);
        Blog blog = (Blog) query.uniqueResult();
        return Optional.ofNullable(blog);
    }

    public ErrorResponse create(Blog blog) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(blog);
            transaction.commit();
            session.close();
            return new ErrorResponse(HttpStatus.OK.value());
        }
        catch (Exception exception) {
            transaction.rollback();
            session.close();

            Throwable rootCause = getRootCause(exception);
            if (rootCause instanceof PSQLException) {
                return new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    exception.getMessage(),
                    rootCause.getMessage());
            }

            return new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "SQL Runtime error",
                    exception.getMessage());
        }
    }

    public ErrorResponse update(Blog blog) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(blog);
            transaction.commit();
            session.close();
            return new ErrorResponse(HttpStatus.OK.value());
        }
        catch (Exception exception) {
            transaction.rollback();
            session.close();

            Throwable rootCause = getRootCause(exception);
            if (rootCause instanceof PSQLException) {
                return new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage(),
                        rootCause.getMessage());
            }

            return new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "SQL Runtime error",
                    exception.getMessage());
        }
    }

    public ErrorResponse remove(UUID id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Optional<Blog> targetingBlog = findById(id);
            targetingBlog.ifPresent(blog -> {
                blog.setDeleteAt(Timestamp.valueOf(LocalDateTime.now()));
                session.merge(blog);
            });
            transaction.commit();
            session.close();
            return new ErrorResponse(HttpStatus.OK.value());
        }
        catch (Exception exception) {
            transaction.rollback();
            session.close();

            Throwable rootCause = getRootCause(exception);

            if (rootCause instanceof PSQLException) {
                return new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage(),
                        rootCause.getMessage());
            }

            return new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "SQL Runtime error",
                    exception.getMessage());
        }
    }
}
