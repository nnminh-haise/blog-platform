package com.proj.projblogplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryResponse {
    private String message;

    private Boolean status;

    public RepositoryResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public RepositoryResponse setStatus(Boolean status) {
        this.status = status;
        return this;
    }
}
