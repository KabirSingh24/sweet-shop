package com.sweet_shop_backend.backend.common.dto;

import com.sweet_shop_backend.backend.sweet.model.Category;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SweetResponse {
    private Long id;
    private String name;
    private Category category;
    private BigDecimal price;
    private Integer quantity;
}