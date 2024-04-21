package com.example.javaee.repository;

import com.example.javaee.dto.ErrorResponse;
import com.example.javaee.exceptions.ResourceNotFoundException;
import com.example.javaee.model.CategoryDetail;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.core.NestedExceptionUtils.getRootCause;

@Repository
public class CategoryDetailRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<CategoryDetail> findAll() {
        Session session = sessionFactory.getCurrentSession();
        String findAllCategoryDetailsQuery = "SELECT cd FROM CategoryDetail AS cd WHERE cd.deleteAt IS NULL";
        Query<CategoryDetail> query = session.createQuery(findAllCategoryDetailsQuery, CategoryDetail.class);
        List<CategoryDetail> categoryDetails = query.list();

        if (categoryDetails.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any category detail!");
        }
        return categoryDetails;
    }

    @Transactional
    public Optional<CategoryDetail> findById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        String findByIdQuery = "SELECT cd FROM CategoryDetail AS cd WHERE cd.deleteAt IS NULL AND cd.id = :id";
        Query<CategoryDetail> query = session.createQuery(findByIdQuery, CategoryDetail.class);
        query.setParameter("id", id);
        CategoryDetail categoryDetail = (CategoryDetail) query.uniqueResult();
        return Optional.ofNullable(categoryDetail);
    }

    @Transactional
    public List<CategoryDetail> findByCategoryId(UUID id) {
        Session session = sessionFactory.openSession();
        String findCategoryDetailByCategoryIdQuery =
                "SELECT cd FROM CategoryDetail AS cd WHERE cd.deleteAt IS NULL and cd.category.id = :id";

        Query<CategoryDetail> query = session.createQuery(findCategoryDetailByCategoryIdQuery, CategoryDetail.class);
        query.setParameter("id", id);

        List<CategoryDetail> categoryDetails = query.list();

        if (categoryDetails.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any category detail has category id of " + id);
        }
        return categoryDetails;
    }

    @Transactional
    public List<CategoryDetail> findByBlogId(UUID id) {
        Session session = sessionFactory.openSession();
        String findCategoryDetailByblogIdQuery =
                "SELECT cd FROM CategoryDetail AS cd WHERE cd.deleteAt IS NULL and cd.blog.id = :id";

        Query<CategoryDetail> query = session.createQuery(findCategoryDetailByblogIdQuery, CategoryDetail.class);
        query.setParameter("id", id);

        List<CategoryDetail> categoryDetails = query.list();

        if (categoryDetails.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any category detail has blog id of " + id);
        }
        return categoryDetails;
    }

    public ErrorResponse create(CategoryDetail categoryDetail) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(categoryDetail);
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

    public ErrorResponse update(CategoryDetail categoryDetail) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(categoryDetail);
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
            Optional<CategoryDetail> targetingCategoryDetail = findById(id);
            targetingCategoryDetail.ifPresent(categoryDetail -> {
                categoryDetail.setDeleteAt(Timestamp.valueOf(LocalDateTime.now()));
                session.merge(categoryDetail);
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
