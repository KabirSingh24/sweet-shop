package com.sweet_shop_backend.backend.sweet.repository;

import com.sweet_shop_backend.backend.sweet.model.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SweetRepository extends JpaRepository<Sweet,Long> {
}
