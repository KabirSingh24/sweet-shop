package com.sweet_shop_backend.backend.sweet.controller;

import com.sweet_shop_backend.backend.auth.model.User;
import com.sweet_shop_backend.backend.common.dto.SweetRequest;
import com.sweet_shop_backend.backend.common.dto.SweetResponse;
import com.sweet_shop_backend.backend.sweet.model.Category;
import com.sweet_shop_backend.backend.sweet.service.SweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/sweets")
@RequiredArgsConstructor
public class SweetController {

    private final SweetService sweetService;

    @PostMapping
    public SweetResponse addSweet(@RequestBody SweetRequest request,
                                  @AuthenticationPrincipal User user) {
        return sweetService.addSweet(request, user.getId());
    }

    @GetMapping
    public List<SweetResponse> getAllSweets(@AuthenticationPrincipal User user) {
        return sweetService.getAllSweets();
    }

    @GetMapping("/search")
    public List<SweetResponse> searchSweets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @AuthenticationPrincipal User user) {
        return sweetService.searchSweets(name, category, minPrice, maxPrice);
    }

    @PutMapping("/{id}")
    public SweetResponse updateSweet(@PathVariable Long id,
                                     @RequestBody SweetRequest request,
                                     @AuthenticationPrincipal User user) {
        return sweetService.updateSweet(id, request, user.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteSweet(@PathVariable Long id,
                            @AuthenticationPrincipal User user) {
        sweetService.deleteSweet(id, user);
    }
    @PostMapping("/{id}/purchase")
    public SweetResponse purchaseSweet(@PathVariable Long id,
                                       @RequestParam int quantity,
                                       @AuthenticationPrincipal User user) {
        return sweetService.purchaseSweet(id, user.getId(), quantity);
    }

    @PostMapping("/{id}/restock")
    public SweetResponse restockSweet(@PathVariable Long id,
                                      @RequestParam int quantity,
                                      @AuthenticationPrincipal User user) {
        return sweetService.restockSweet(id, user.getId(), quantity);
    }

}
