package dev.yoon.basic_board.service;

import dev.yoon.basic_board.domain.Shop;
import dev.yoon.basic_board.domain.User;
import dev.yoon.basic_board.dto.shop.ShopDto;
import dev.yoon.basic_board.repository.ShopRepository;
import dev.yoon.basic_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public ShopDto createShop(ShopDto shopDto) {

        Long userId = shopDto.getUserId();
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty() || optionalUser.get().getVerify() == false)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        User user = optionalUser.get();
        Shop shop = new Shop(shopDto);
        user.addShop(shop);

        shopRepository.save(shop);
        return shopDto;

    }

    public List<ShopDto> readShopAll() {

        List<Shop> shopList = shopRepository.findAll();

        return shopList.stream()
                .map(shop -> new ShopDto(shop))
                .collect(Collectors.toList());
    }

    public ShopDto readShopOne(Long shopId) {
        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        if (optionalShop.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ShopDto dto = new ShopDto(optionalShop.get());
        return dto;
    }

    public boolean updateShop(Long shopId, ShopDto shopDto) {
        Optional<Shop> optionalShop = shopRepository.findById(shopId);

        if (optionalShop.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Shop shop = optionalShop.get();
        // ?????? ???????????? ?????? ?????? ??????
        if (shopDto.getUserId() != shop.getUser().getId())
            return false;


        shop.update(shopDto);
        return true;
    }

    public boolean deleteShop(Long shopId) {
        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        if (optionalShop.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        shopRepository.deleteById(shopId);
        return true;

    }
}
