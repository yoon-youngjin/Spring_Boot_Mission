package dev.yoon.basic_board.repository;

import dev.yoon.basic_board.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
