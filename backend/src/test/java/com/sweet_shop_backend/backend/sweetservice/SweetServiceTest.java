package com.sweet_shop_backend.backend.sweetservice;


import com.sweet_shop_backend.backend.auth.model.Role;
import com.sweet_shop_backend.backend.auth.model.User;
import com.sweet_shop_backend.backend.auth.repository.UserRepository;
import com.sweet_shop_backend.backend.common.dto.SweetRequest;
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
import org.junit.jupiter.api.*;


@SpringBootTest
@Transactional
public class SweetServiceTest {

    @Autowired
    private SweetService sweetService;

    @Autowired
    private SweetRepository sweetRepository;

    @Autowired
    private UserRepository userRepository;

    private User owner;
    private User otherUser;

    @BeforeEach
    public void setup() {
        sweetRepository.deleteAll();
        userRepository.deleteAll();

        owner = User.builder()
                .email("owner@example.com")
                .password("encodedPass")
                .role(Role.USER)
                .build();
        userRepository.save(owner);

        otherUser = User.builder()
                .email("other@example.com")
                .password("encodedPass")
                .role(Role.USER)
                .build();
        userRepository.save(otherUser);
    }

    @Test
    public void testAddSweet_Success() {
        SweetRequest request = SweetRequest.builder()
                .name("Chocolate")
                .category(Category.CANDY)
                .price(BigDecimal.valueOf(50.00))
                .quantity(10)
                .build();

        SweetResponse saved = sweetService.addSweet(request, owner.getId());

        assertNotNull(saved.getId());
        assertEquals("Chocolate", saved.getName());
        assertEquals(Category.CANDY, saved.getCategory());
        assertEquals(BigDecimal.valueOf(50.00), saved.getPrice());
        assertEquals(10, saved.getQuantity());

        // Check that createdBy is correctly set
        Sweet persisted = sweetRepository.findById(saved.getId()).orElseThrow();
        assertEquals(owner.getId(), persisted.getCreatedByUser().getId());
    }

    @Test
    public void testUpdateSweet_Success() {
        // First, create a sweet
        Sweet sweet = Sweet.builder()
                .name("Chocolate")
                .category(Category.CANDY)
                .price(BigDecimal.valueOf(50.00))
                .quantity(10)
                .createdByUser(owner)
                .build();
        sweetRepository.save(sweet);

        SweetRequest updateRequest = SweetRequest.builder()
                .name("Dark Chocolate")
                .category(Category.CHOCOLATE)
                .price(BigDecimal.valueOf(60.00))
                .quantity(15)
                .build();

        SweetResponse updated = sweetService.updateSweet(sweet.getId(), updateRequest, owner.getId());

        assertEquals("Dark Chocolate", updated.getName());
        assertEquals(Category.CHOCOLATE, updated.getCategory());
        assertEquals(BigDecimal.valueOf(60.00), updated.getPrice());
        assertEquals(15, updated.getQuantity());
    }

    @Test
    public void testUpdateSweet_FailsForNonOwner() {
        // Create a sweet by owner
        Sweet sweet = Sweet.builder()
                .name("Chocolate")
                .category(Category.CANDY)
                .price(BigDecimal.valueOf(50.00))
                .quantity(10)
                .createdByUser(owner)
                .build();
        sweetRepository.save(sweet);

        SweetRequest updateRequest = SweetRequest.builder()
                .name("Dark Chocolate")
                .category(Category.CHOCOLATE)
                .price(BigDecimal.valueOf(60.00))
                .quantity(15)
                .build();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sweetService.updateSweet(sweet.getId(), updateRequest, otherUser.getId());
        });

        assertTrue(exception.getMessage().contains("Only creator can update this sweet"));
    }

    @Test
    public void testGetAllSweets_FailsInitially() {
        // Initially, service method not implemented
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sweetService.getAllSweets();
        });
        assertTrue(exception.getMessage().contains("Not implemented"));
    }

}
