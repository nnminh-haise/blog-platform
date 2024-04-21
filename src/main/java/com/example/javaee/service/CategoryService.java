package com.example.javaee.service;

import com.example.javaee.dto.CreateCategoryDto;
import com.example.javaee.dto.ErrorResponse;
import com.example.javaee.dto.ResponseDto;
import com.example.javaee.dto.UpdateCategoryDto;
import com.example.javaee.exceptions.ResourceNotFoundException;
import com.example.javaee.model.Category;
import com.example.javaee.repository.CategoryRepository;
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
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    private String getSlug(String title) {
        return title
                .toLowerCase()
                .trim()
                .replace(" ", "-");
    }

    public ResponseDto<Category> create(CreateCategoryDto dto) {
        Date currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
        Category newCategory = new Category();
        newCategory.setName(dto.getName());
        newCategory.setCreateAt(currentTimestamp);
        newCategory.setUpdateAt(currentTimestamp);
        newCategory.setSlug(getSlug(dto.getName()));

        ErrorResponse errorResponse = this.categoryRepository.save(newCategory);

        return errorResponse.ifHasErrorOrElse(
                () -> new ResponseDto<>(
                        errorResponse.getStatus(),
                        errorResponse.getMessage()),
                // * otherwise
                () -> new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Success", newCategory));
    }

    public ResponseDto<List<Category>> findAll() {
        try {
            List<Category> categories = this.categoryRepository.findAll();
            return new ResponseDto<>(
                    HttpStatus.OK.value(),
                    "Success",
                    categories);
        }
        catch (ResourceNotFoundException exception) {
            return new ResponseDto<>(
                    HttpStatus.NOT_FOUND.value(),
                    exception.getMessage());
        }
    }

    public ResponseDto<Category> findById(UUID id) {
        if (id == null) {
            return new ResponseDto<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid ID");
        }

        Optional<Category> category = this.categoryRepository.findById(id);
        return category.map(value -> new ResponseDto<>(
                HttpStatus.OK.value(),
                "Success",
                value)).orElseGet(() -> new ResponseDto<>(
                HttpStatus.NOT_FOUND.value(),
                "Cannot find any blog with the given ID"));
    }

    public ResponseDto<Category> update(UUID id, UpdateCategoryDto dto) {
        Optional<Category> targetingCategory = this.categoryRepository.findById(id);
        if (!targetingCategory.isPresent()) {
            return new ResponseDto<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Cannot find any category with the given ID");
        }

        Category newCategory = targetingCategory.get();

        ErrorResponse errorResponse = this.categoryRepository.update(newCategory);
        return errorResponse.ifHasErrorOrElse(
                () -> new ResponseDto<>(
                        errorResponse.getStatus(),
                        errorResponse.getMessage()),
                // * otherwise
                () -> new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Success", newCategory));
    }

    public ResponseDto<Category> remove(UUID id) {
        ErrorResponse errorResponse = this.categoryRepository.remove(id);
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
