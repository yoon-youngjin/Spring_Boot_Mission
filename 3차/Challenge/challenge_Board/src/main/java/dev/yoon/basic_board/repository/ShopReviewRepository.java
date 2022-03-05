package dev.yoon.basic_board.repository;

import dev.yoon.basic_board.domain.ShopReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopReviewRepository extends JpaRepository<ShopReview,Long> {
}
