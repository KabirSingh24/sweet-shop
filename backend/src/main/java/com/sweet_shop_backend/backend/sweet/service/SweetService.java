package com.sweet_shop_backend.backend.sweet.service;


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

    public SweetResponse addSweet(SweetRequest sweetRequest) {
        if (sweetRequest == null) throw new RuntimeException("Sweet cannot be null");

        Sweet sweet=Sweet.builder()
                .category(sweetRequest.getCategory())
                .name(sweetRequest.getName())
                .price(sweetRequest.getPrice())
                .quantity(sweetRequest.getQuantity())
                .build();

        Sweet saved=sweetRepository.save(sweet);
        return SweetResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .category(saved.getCategory())
                .price(saved.getPrice())
                .quantity(saved.getQuantity())
                .build();
    }
}
