package dev.yoon.basic_board.controller;

import dev.yoon.basic_board.dto.shop.ShopDto;
import dev.yoon.basic_board.dto.shop.ShopPostDto;
import dev.yoon.basic_board.service.ShopPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("shop-post")
@RequiredArgsConstructor
public class ShopPostController {

    private final ShopPostService shopPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShopPostDto> creatShopPost(
            @RequestBody ShopPostDto shopPostDto) {
        ShopPostDto dto = this.shopPostService.createShopPost(shopPostDto);

        if(dto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dto);
    }
}
