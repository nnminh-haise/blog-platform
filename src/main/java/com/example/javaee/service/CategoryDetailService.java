package com.example.javaee.service;

import com.example.javaee.dto.CreateCategoryDetailDto;
import com.example.javaee.dto.ErrorResponse;
import com.example.javaee.dto.ResponseDto;
import com.example.javaee.dto.UpdateCategoryDetailDto;
import com.example.javaee.exceptions.ResourceNotFoundException;
import com.example.javaee.model.Blog;
import com.example.javaee.model.Category;
import com.example.javaee.model.CategoryDetail;
import com.example.javaee.repository.BlogRepository;
import com.example.javaee.repository.CategoryDetailRepository;
import com.example.javaee.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryDetailService {
    @Autowired
    private CategoryDetailRepository categoryDetailRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BlogRepository blogRepository;

    public ResponseDto<CategoryDetail> create(CreateCategoryDetailDto createCategoryDetailDto) {
        Optional<Category> targetingCategory = categoryRepository.findById(
                createCategoryDetailDto.getCategoryId());
        if (!targetingCategory.isPresent()) {
            return new ResponseDto<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Cannot find any category with the given ID");
        }

        Optional<Blog> targetingBlog = blogRepository.findById(
                createCategoryDetailDto.getBlogId());
        if (!targetingBlog.isPresent()) {
            return new ResponseDto<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Cannot find any blog with the given ID");
        }

        CategoryDetail newCategoryDetail = new CategoryDetail(
                targetingCategory.get(), targetingBlog.get());
        ErrorResponse response = this.categoryDetailRepository.create(newCategoryDetail);

        return response.ifHasErrorOrElse(
                () -> new ResponseDto<>(
                        response.getStatus(),
                        response.getMessage()),
                () -> new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Success", newCategoryDetail));
    }

    public ResponseDto<List<CategoryDetail>> findAll() {
        try {
            List<CategoryDetail> categoryDetails = this.categoryDetailRepository.findAll();
            return new ResponseDto<>(
                    HttpStatus.OK.value(),
                    "Success",
                    categoryDetails);
        }
        catch (ResourceNotFoundException exception) {
            return new ResponseDto<>(
                    HttpStatus.NOT_FOUND.value(),
                    exception.getMessage());
        }
    }

    public ResponseDto<CategoryDetail> findById(UUID id) {
        if (id == null) {
            return new ResponseDto<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid ID");
        }

        Optional<CategoryDetail> categoryDetail = this.categoryDetailRepository.findById(id);
        return categoryDetail.map(value -> new ResponseDto<>(
                HttpStatus.OK.value(),
                "Success",
                value)).orElseGet(() -> new ResponseDto<>(
                HttpStatus.NOT_FOUND.value(),
                "Cannot find any category detail with the given ID"));
    }

    // TODO: Consider removing this method
    // ! This method is now update nothing!
    public ResponseDto<CategoryDetail> update(UUID id, UpdateCategoryDetailDto dto) {
        Optional<CategoryDetail> targetingCategoryDetail = this.categoryDetailRepository.findById(id);
        if (!targetingCategoryDetail.isPresent()) {
            return new ResponseDto<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Cannot find any category detail with the given ID");
        }
        // ! This update nothing
        ErrorResponse errorResponse = this.categoryDetailRepository.update(targetingCategoryDetail.get());
        return errorResponse.ifHasErrorOrElse(
                () -> new ResponseDto<>(
                        errorResponse.getStatus(),
                        errorResponse.getMessage()),
                // * otherwise
                () -> new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Success"));
    }

    public ResponseDto<CategoryDetail> remove(UUID id) {
        ErrorResponse errorResponse = this.categoryDetailRepository.remove(id);
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
