package com.sweet_shop_backend.backend.sweet.service;


import com.sweet_shop_backend.backend.auth.model.Role;
import com.sweet_shop_backend.backend.auth.model.User;
import com.sweet_shop_backend.backend.auth.repository.UserRepository;
import com.sweet_shop_backend.backend.common.dto.SweetRequest;
import com.sweet_shop_backend.backend.common.dto.SweetResponse;
import com.sweet_shop_backend.backend.sweet.model.Category;
import com.sweet_shop_backend.backend.sweet.model.Sweet;
import com.sweet_shop_backend.backend.sweet.repository.SweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SweetService {

    private final SweetRepository sweetRepository;
    private final UserRepository userRepository;

    public List<SweetResponse> getAllSweets() {
        List<Sweet> sweets = sweetRepository.findAll();

        return sweets.stream()
                .map(s -> SweetResponse.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .category(s.getCategory())
                        .price(s.getPrice())
                        .quantity(s.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    public List<SweetResponse> searchSweets(String name, Category category, BigDecimal minPrice, BigDecimal maxPrice) {
        List<Sweet> sweets = sweetRepository.findAll().stream()
                .filter(s -> (name == null || s.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(s -> (category == null || s.getCategory() == category))
                .filter(s -> (minPrice == null || s.getPrice().compareTo(minPrice) >= 0))
                .filter(s -> (maxPrice == null || s.getPrice().compareTo(maxPrice) <= 0))
                .collect(Collectors.toList());

        return sweets.stream()
                .map(s -> SweetResponse.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .category(s.getCategory())
                        .price(s.getPrice())
                        .quantity(s.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }


    public SweetResponse addSweet(SweetRequest sweetRequest, Long userId) {
        if (sweetRequest == null) throw new RuntimeException("Sweet cannot be null");

        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Sweet sweet = Sweet.builder()
                .name(sweetRequest.getName())
                .category(sweetRequest.getCategory())
                .price(sweetRequest.getPrice())
                .quantity(sweetRequest.getQuantity())
                .createdByUser(creator)
                .build();

        Sweet saved = sweetRepository.save(sweet);

        return SweetResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .category(saved.getCategory())
                .price(saved.getPrice())
                .quantity(saved.getQuantity())
                .build();
    }

    public SweetResponse updateSweet(Long id, SweetRequest sweetRequest, Long userId) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));


        sweet.setName(sweetRequest.getName());
        sweet.setCategory(sweetRequest.getCategory());
        sweet.setPrice(sweetRequest.getPrice());
        sweet.setQuantity(sweetRequest.getQuantity());

        Sweet updated = sweetRepository.save(sweet);

        return SweetResponse.builder()
                .id(updated.getId())
                .name(updated.getName())
                .category(updated.getCategory())
                .price(updated.getPrice())
                .quantity(updated.getQuantity())
                .build();
    }


    public void deleteSweet(Long id, User currentUser) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if (currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin can delete a sweet");
        }

        sweetRepository.delete(sweet);
    }

    public SweetResponse purchaseSweet(Long sweetId, Long userId, int quantity) {
        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if (sweet.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient quantity");
        }

        sweet.setQuantity(sweet.getQuantity() - quantity);
        Sweet updated = sweetRepository.save(sweet);

        return SweetResponse.builder()
                .id(updated.getId())
                .name(updated.getName())
                .category(updated.getCategory())
                .price(updated.getPrice())
                .quantity(updated.getQuantity())
                .build();
    }

    public SweetResponse restockSweet(Long sweetId, Long userId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin can restock");
        }

        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        sweet.setQuantity(sweet.getQuantity() + quantity);
        Sweet updated = sweetRepository.save(sweet);

        return SweetResponse.builder()
                .id(updated.getId())
                .name(updated.getName())
                .category(updated.getCategory())
                .price(updated.getPrice())
                .quantity(updated.getQuantity())
                .build();
    }



}
