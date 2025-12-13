package com.sweet_shop_backend.backend.sweetservice;

import com.sweet_shop_backend.backend.auth.model.Role;
import com.sweet_shop_backend.backend.auth.model.User;
import com.sweet_shop_backend.backend.auth.repository.UserRepository;
import com.sweet_shop_backend.backend.common.dto.SweetResponse;
import com.sweet_shop_backend.backend.sweet.model.Category;
import com.sweet_shop_backend.backend.sweet.model.Sweet;
import com.sweet_shop_backend.backend.sweet.repository.SweetRepository;
import com.sweet_shop_backend.backend.sweet.service.SweetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

@SpringBootTest
@Transactional
public class SweetInventoryServiceTest {

    @Autowired
    private SweetService sweetService;

    @Autowired
    private SweetRepository sweetRepository;

    @Autowired
    private UserRepository userRepository;

    private User owner;
    private User admin;
    private Sweet sweet;

    @BeforeEach
    public void setup() {
        sweetRepository.deleteAll();
        userRepository.deleteAll();

        owner = User.builder().email("user@example.com").password("encodedPass").role(Role.USER).build();
        admin = User.builder().email("admin@example.com").password("encodedPass").role(Role.ADMIN).build();
        userRepository.save(owner);
        userRepository.save(admin);

        sweet = Sweet.builder()
                .name("Chocolate")
                .category(Category.CANDY)
                .price(BigDecimal.valueOf(50))
                .quantity(10)
                .createdByUser(owner)
                .build();
        sweetRepository.save(sweet);
    }

    @Test
    public void testPurchaseSweet_Success() {
        SweetResponse response = sweetService.purchaseSweet(sweet.getId(), owner.getId(), 5);
        assertEquals(5, response.getQuantity());
        Sweet updated = sweetRepository.findById(sweet.getId()).orElseThrow();
        assertEquals(5, updated.getQuantity());
    }
    @Test
    public void testPurchaseSweet_InsufficientQuantity() {
        Exception ex = assertThrows(RuntimeException.class, () -> {
            sweetService.purchaseSweet(sweet.getId(), owner.getId(), 15);
        });
        assertTrue(ex.getMessage().contains("Insufficient quantity"));
    }

    @Test
    public void testRestockSweet_Success_Admin() {
        SweetResponse response = sweetService.restockSweet(sweet.getId(), admin.getId(), 20);
        assertEquals(30, response.getQuantity());
    }
}
