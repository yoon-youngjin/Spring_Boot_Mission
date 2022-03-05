package dev.yoon.basic_board.service;

import dev.yoon.basic_board.domain.Shop;
import dev.yoon.basic_board.domain.ShopReview;
import dev.yoon.basic_board.domain.User;
import dev.yoon.basic_board.dto.shop.ShopReviewDto;
import dev.yoon.basic_board.repository.ShopRepository;
import dev.yoon.basic_board.repository.ShopReviewRepository;
import dev.yoon.basic_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ShopReviewService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ShopReviewRepository shopReviewRepository;

    public ShopReviewDto createShopReview(ShopReviewDto shopReviewDto) {

        Optional<User> optionalUser = userRepository.findById(shopReviewDto.getUserId());
        Optional<Shop> optionalShop = shopRepository.findById(shopReviewDto.getShopId());

        if(optionalUser.isEmpty() || optionalShop.isEmpty())
            return null;

        User user = optionalUser.get();
        Shop shop = optionalShop.get();

        ShopReview shopReview = new ShopReview(shopReviewDto);
        shop.addShopReview(shopReview);
        user.addShopReview(shopReview);

        shopReviewRepository.save(shopReview);

        return shopReviewDto;
    }
}
