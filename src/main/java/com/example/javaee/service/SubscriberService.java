package com.example.javaee.service;

import com.example.javaee.dto.CreateSubscriberDto;
import com.example.javaee.dto.ErrorResponse;
import com.example.javaee.dto.ResponseDto;
import com.example.javaee.dto.UpdateSubscriberDto;
import com.example.javaee.exceptions.ResourceNotFoundException;
import com.example.javaee.model.Subscriber;
import com.example.javaee.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriberService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    public ResponseDto<Subscriber> create(CreateSubscriberDto dto) {
        Date currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
        Subscriber newSubscriber = new Subscriber();
        newSubscriber.setEmail(dto.getEmail());
        newSubscriber.setFullName(dto.getFullName());
        newSubscriber.setCreateAt(currentTimestamp);
        newSubscriber.setUpdateAt(currentTimestamp);

        ErrorResponse errorResponse = this.subscriberRepository.create(newSubscriber);

        return errorResponse.ifHasErrorOrElse(
                () -> new ResponseDto<>(
                        errorResponse.getStatus(),
                        errorResponse.getMessage()),
                // * otherwise
                () -> new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Success", newSubscriber));
    }

    public ResponseDto<List<Subscriber>> findAll() {
        try {
            List<Subscriber> blogs = this.subscriberRepository.findAll();
            return new ResponseDto<>(
                    HttpStatus.OK.value(),
                    "Success",
                    blogs);
        }
        catch (ResourceNotFoundException exception) {
            return new ResponseDto<>(
                    HttpStatus.NOT_FOUND.value(),
                    exception.getMessage());
        }
    }

    public ResponseDto<Subscriber> findById(UUID id) {
        if (id == null) {
            return new ResponseDto<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid ID");
        }

        Optional<Subscriber> subscriber = this.subscriberRepository.findById(id);
        return subscriber.map(value -> new ResponseDto<>(
                HttpStatus.OK.value(),
                "Success",
                value)).orElseGet(() -> new ResponseDto<>(
                HttpStatus.NOT_FOUND.value(),
                "Cannot find any blog with the given ID"));
    }

    public ResponseDto<Subscriber> update(UUID id, UpdateSubscriberDto dto) {
        Optional<Subscriber> targetingSubscriber = this.subscriberRepository.findById(id);
        if (!targetingSubscriber.isPresent()) {
            return new ResponseDto<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Cannot find any blog with the given ID");
        }

        Subscriber newSubscriber = targetingSubscriber.get();
        if (dto.getEmail() != null) {
            newSubscriber.setEmail(dto.getEmail());
        }
        if (dto.getFullName() != null) {
            newSubscriber.setFullName(dto.getFullName());
        }

        ErrorResponse errorResponse = this.subscriberRepository.update(newSubscriber);
        return errorResponse.ifHasErrorOrElse(
                () -> new ResponseDto<>(
                        errorResponse.getStatus(),
                        errorResponse.getMessage()),
                // * otherwise
                () -> new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Success"));
    }

    public ResponseDto<Subscriber> remove(UUID id) {
        ErrorResponse errorResponse = this.subscriberRepository.remove(id);
        return errorResponse.ifHasErrorOrElse(
                () -> new ResponseDto<>(
                        errorResponse.getStatus(),
                        errorResponse.getMessage()),
                // * otherwise
                () -> new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Success"));
    }
}
