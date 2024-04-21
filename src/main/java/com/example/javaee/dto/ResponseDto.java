package com.example.javaee.dto;

import com.example.javaee.model.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private Integer status;

    private String message;

    private T data;

    public ResponseDto(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public ResponseDto<T> setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ResponseDto<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseDto<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Boolean hasStatus(HttpStatus status) {
        return this.status.equals(status.value());
    }
}
