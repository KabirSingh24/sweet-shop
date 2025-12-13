package com.sweet_shop_backend.backend.sweet.service;


import com.sweet_shop_backend.backend.auth.model.User;
import com.sweet_shop_backend.backend.auth.repository.UserRepository;
import com.sweet_shop_backend.backend.common.dto.SweetRequest;
import com.sweet_shop_backend.backend.common.dto.SweetResponse;
import com.sweet_shop_backend.backend.sweet.model.Sweet;
import com.sweet_shop_backend.backend.sweet.repository.SweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SweetService {

    private final SweetRepository sweetRepository;
    private final UserRepository userRepository;

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

    // Update Sweet only if the user is the creator
    public SweetResponse updateSweet(Long id, SweetRequest sweetRequest, Long userId) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if (!sweet.getCreatedByUser().getId().equals(userId)) {
            throw new RuntimeException("Only creator can update this sweet");
        }

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


}
