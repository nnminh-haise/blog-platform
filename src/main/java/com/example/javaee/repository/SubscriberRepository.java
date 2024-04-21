package com.example.javaee.repository;

import com.example.javaee.dto.ErrorResponse;
import com.example.javaee.exceptions.ResourceNotFoundException;
import com.example.javaee.model.Subscriber;
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
public class SubscriberRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Subscriber> findAll() {
        Session session = sessionFactory.getCurrentSession();
        String findAllSubscriberQuery = "SELECT s FROM Subscriber AS s WHERE s.deleteAt IS NULL";
        Query<Subscriber> query = session.createQuery(findAllSubscriberQuery, Subscriber.class);
        List<Subscriber> subscribers = query.list();

        if (subscribers.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find any blog!");
        }
        return subscribers;
    }

    @Transactional
    public Optional<Subscriber> findById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        String findByIdQuery = "SELECT s FROM Subscriber AS s WHERE s.deleteAt IS NULL AND s.id = :id";
        Query<Subscriber> query = session.createQuery(findByIdQuery, Subscriber.class);
        query.setParameter("id", id);
        Subscriber subscriber = (Subscriber) query.uniqueResult();
        return Optional.ofNullable(subscriber);
    }

    public ErrorResponse create(Subscriber subscriber) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(subscriber);
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

    public ErrorResponse update(Subscriber subscriber) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(subscriber);
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
            Optional<Subscriber> targetingSubscriber = findById(id);
            targetingSubscriber.ifPresent(subscriber -> {
                subscriber.setDeleteAt(Timestamp.valueOf(LocalDateTime.now()));
                session.merge(subscriber);
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
