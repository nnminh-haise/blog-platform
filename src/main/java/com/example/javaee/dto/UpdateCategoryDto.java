package com.example.javaee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
//@NoArgsConstructor
public class UpdateCategoryDto extends CreateCategoryDto {
}
