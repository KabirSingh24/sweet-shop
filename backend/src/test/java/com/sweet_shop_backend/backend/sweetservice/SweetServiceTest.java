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
import java.util.List;

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
    public void testUpdateSweet_SuccessForAnyUser() {
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

        // Now any user can update
        SweetResponse updated = sweetService.updateSweet(sweet.getId(), updateRequest, otherUser.getId());

        assertEquals("Dark Chocolate", updated.getName());
        assertEquals(Category.CHOCOLATE, updated.getCategory());
        assertEquals(BigDecimal.valueOf(60.00), updated.getPrice());
        assertEquals(15, updated.getQuantity());
    }


    @Test
    public void testGetAllSweets_FailsInitially() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sweetService.getAllSweets();
        });
        assertTrue(exception.getMessage().contains("Not implemented"));
    }
    @Test
    public void testGetAllSweets_ReturnsCorrectSweets() {
        Sweet sweet1 = Sweet.builder().name("Chocolate").category(Category.CANDY).price(BigDecimal.valueOf(50)).quantity(10).createdByUser(owner).build();
        Sweet sweet2 = Sweet.builder().name("Lollipop").category(Category.CANDY).price(BigDecimal.valueOf(20)).quantity(5).createdByUser(owner).build();
        sweetRepository.save(sweet1);
        sweetRepository.save(sweet2);

        List<SweetResponse> sweets = sweetService.getAllSweets();

        assertEquals(2, sweets.size());
        assertTrue(sweets.stream().anyMatch(s -> s.getName().equals("Chocolate")));
        assertTrue(sweets.stream().anyMatch(s -> s.getName().equals("Lollipop")));
    }

    @Test
    public void testSearchSweets_FailsInitially() {
        SweetRequest request = SweetRequest.builder()
                .name("Chocolate")
                .category(Category.CANDY)
                .price(BigDecimal.valueOf(50))
                .quantity(10)
                .build();

        Sweet sweet = Sweet.builder()
                .name(request.getName())
                .category(request.getCategory())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .createdByUser(owner)
                .build();
        sweetRepository.save(sweet);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sweetService.searchSweets("Chocolate", Category.CANDY, BigDecimal.valueOf(40), BigDecimal.valueOf(60));
        });

        assertTrue(exception.getMessage().contains("Not implemented"));
    }

    @Test
    public void testSearchSweets_ReturnsCorrectResults() {
        Sweet sweet1 = Sweet.builder().name("Chocolate").category(Category.CANDY).price(BigDecimal.valueOf(50)).quantity(10).createdByUser(owner).build();
        Sweet sweet2 = Sweet.builder().name("Dark Chocolate").category(Category.CHOCOLATE).price(BigDecimal.valueOf(60)).quantity(5).createdByUser(owner).build();
        sweetRepository.save(sweet1);
        sweetRepository.save(sweet2);

        List<SweetResponse> results = sweetService.searchSweets("Chocolate", null, BigDecimal.valueOf(40), BigDecimal.valueOf(55));

        assertEquals(1, results.size());
        assertEquals("Chocolate", results.get(0).getName());
    }

    @Test
    public void testDeleteSweet_FailsInitially() {
        Sweet sweet = Sweet.builder()
                .name("Chocolate")
                .category(Category.CANDY)
                .price(BigDecimal.valueOf(50))
                .quantity(10)
                .createdByUser(owner)
                .build();
        sweetRepository.save(sweet);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sweetService.deleteSweet(sweet.getId(), owner);
        });

        assertTrue(exception.getMessage().contains("Not implemented"));
    }

    @Test
    public void testDeleteSweet_Success_Admin() {
        User admin = User.builder()
                .email("admin@example.com")
                .password("encodedPass")
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);

        Sweet sweet = Sweet.builder()
                .name("Chocolate")
                .category(Category.CANDY)
                .price(BigDecimal.valueOf(50))
                .quantity(10)
                .createdByUser(owner)
                .build();
        sweetRepository.save(sweet);

        sweetService.deleteSweet(sweet.getId(), admin);

        assertFalse(sweetRepository.findById(sweet.getId()).isPresent());
    }




}
