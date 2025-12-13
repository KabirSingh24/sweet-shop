package com.sweet_shop_backend.backend.sweet.model;

import com.sweet_shop_backend.backend.common.utils.AuditField;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sweets")
public class Sweet extends AuditField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;
}
