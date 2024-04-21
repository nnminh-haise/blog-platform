package com.example.javaee.service;

import com.example.javaee.dto.CreateBlogDto;
import com.example.javaee.dto.ErrorResponse;
import com.example.javaee.dto.ResponseDto;
import com.example.javaee.dto.UpdateBlogDto;
import com.example.javaee.exceptions.ResourceNotFoundException;
import com.example.javaee.model.Blog;
import com.example.javaee.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    private String getSlug(String title) {
        return title
                .toLowerCase()
                .trim()
                .replace(" ", "-");
    }

    // TODO: update this method logic to remove the bypass constrains code
    public ResponseDto<Blog> create(CreateBlogDto dto) {
        Date currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
        Blog newBlog = new Blog();
        newBlog.setTitle(dto.getTitle());
        newBlog.setDescription(dto.getDescription());
        // ! This is a temporary dummy code to bypass the not null constrains
        newBlog.setAttachment(dto.getTitle() + "'s attachments");
        newBlog.setCreateAt(currentTimestamp);
        newBlog.setUpdateAt(currentTimestamp);
        newBlog.setSlug(getSlug(dto.getTitle()));

        ErrorResponse errorResponse = this.blogRepository.create(newBlog);

        return errorResponse.ifHasErrorOrElse(
                () -> new ResponseDto<>(
                        errorResponse.getStatus(),
                        errorResponse.getMessage()),
                // * otherwise
                () -> new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Success", newBlog));
    }

    public ResponseDto<List<Blog>> findAll() {
        try {
            List<Blog> blogs = this.blogRepository.findAll();
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

    public ResponseDto<Blog> findById(UUID id) {
        if (id == null) {
            return new ResponseDto<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid ID");
        }

        Optional<Blog> blog = this.blogRepository.findById(id);
        return blog.map(value -> new ResponseDto<>(
                HttpStatus.OK.value(),
                "Success",
                value)).orElseGet(() -> new ResponseDto<>(
                HttpStatus.NOT_FOUND.value(),
                "Cannot find any blog with the given ID"));
    }

    public ResponseDto<Blog> update(UUID id, UpdateBlogDto dto) {
        Optional<Blog> targetingBlog = this.blogRepository.findById(id);
        if (!targetingBlog.isPresent()) {
            return new ResponseDto<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Cannot find any blog with the given ID");
        }

        Blog newBlog = targetingBlog.get();
        newBlog.setTitle(dto.getTitle());
        newBlog.setDescription(dto.getDescription());
        newBlog.setAttachment(dto.getAttachment());
        newBlog.setSlug(getSlug(newBlog.getTitle()));
        newBlog.setPublishAt(dto.getPublishAt());
        newBlog.setHiddenStatus(dto.getHiddenStatus());

        ErrorResponse errorResponse = this.blogRepository.update(newBlog);
        return errorResponse.ifHasErrorOrElse(
                () -> new ResponseDto<>(
                        errorResponse.getStatus(),
                        errorResponse.getMessage()),
                // * otherwise
                () -> new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Success"));
    }

    public ResponseDto<Blog> remove(UUID id) {
        ErrorResponse errorResponse = this.blogRepository.remove(id);
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
