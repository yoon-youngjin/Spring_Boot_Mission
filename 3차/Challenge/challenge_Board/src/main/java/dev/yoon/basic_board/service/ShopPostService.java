package dev.yoon.basic_board.service;

import dev.yoon.basic_board.domain.Shop;
import dev.yoon.basic_board.domain.ShopPost;
import dev.yoon.basic_board.domain.User;
import dev.yoon.basic_board.dto.shop.ShopPostDto;
import dev.yoon.basic_board.repository.ShopPostRepository;
import dev.yoon.basic_board.repository.ShopRepository;
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
public class ShopPostService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ShopPostRepository shopPostRepository;

    public ShopPostDto createShopPost(ShopPostDto shopPostDto) {

        Optional<User> optionalUser = userRepository.findById(shopPostDto.getUserId());
        Optional<Shop> optionalShop = shopRepository.findById(shopPostDto.getShopId());

        if(optionalUser.isEmpty() || optionalShop.isEmpty())
            return null;

        User user = optionalUser.get();
        Shop shop = optionalShop.get();

        if(shop.getUser() != user)
            return null;

        ShopPost shopPost = new ShopPost(shopPostDto);
        shop.addShopPost(shopPost);
        user.addShopPost(shopPost);

        shopPostRepository.save(shopPost);

        return shopPostDto;
    }


}
