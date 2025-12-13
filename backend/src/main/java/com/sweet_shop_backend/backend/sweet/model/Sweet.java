package com.sweet_shop_backend.backend.sweet.model;

import com.sweet_shop_backend.backend.auth.model.User;
import com.sweet_shop_backend.backend.common.utils.AuditField;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sweets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdByUser;
}
