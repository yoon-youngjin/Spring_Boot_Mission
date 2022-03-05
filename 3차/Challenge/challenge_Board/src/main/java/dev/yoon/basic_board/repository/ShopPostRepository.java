package dev.yoon.basic_board.repository;

import dev.yoon.basic_board.domain.ShopPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopPostRepository extends JpaRepository<ShopPost,Long> {
}
