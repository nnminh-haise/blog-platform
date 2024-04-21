package com.example.javaee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.function.Supplier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private Integer status;

    private String error;

    private String message;

    public ErrorResponse(Integer status) {
        this.status = status;
    }

    public Boolean hasError() {
        return status != HttpStatus.OK.value();
    }

    public static ErrorResponse noError() {
        return new ErrorResponse(HttpStatus.OK.value());
    }

    public <T> T ifHasError(Supplier<T> supplier) {
        if (hasError()) {
            return supplier.get();
        }
        return null;
    }

    public <T> T ifHasErrorOrElse(Supplier<T> supplier, Supplier<T> otherSupplier) {
        if (hasError()) {
            return supplier.get();
        }
        return otherSupplier.get();
    }

    public void ifHasError(Runnable action) {
        if (hasError()) {
            action.run();
        }
    }

    public <T> T ifNoError(Supplier<T> supplier) {
        if (hasError()) {
            return supplier.get();
        }
        return null;
    }

    public <T> T ifNoErrorOrElse(Supplier<T> supplier, Supplier<T> otherSupplier) {
        if (hasError()) {
            return supplier.get();
        }
        return otherSupplier.get();
    }

    public void ifNoError(Runnable action) {
        if (hasError()) {
            action.run();
        }
    }
}
