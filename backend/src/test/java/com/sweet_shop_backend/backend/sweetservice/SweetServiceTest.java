package com.sweet_shop_backend.backend.sweetservice;


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
public class SweetServiceTest {

    @Autowired
    private SweetService sweetService;

    @Autowired
    private SweetRepository sweetRepository;

    @BeforeEach
    public void setup() {
        sweetRepository.deleteAll();
    }

    @Test
    public void testAddSweet_FailsInitially() {
        // This is the Red test: fail because method not implemented yet
        Sweet sweet = Sweet.builder()
                .name("Chocolate")
                .category(Category.CANDY)
                .price(BigDecimal.valueOf(50.00))
                .quantity(10)
                .build();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sweetService.addSweet(sweet);
        });

        assertTrue(exception.getMessage().contains("Not implemented"));
    }
}
