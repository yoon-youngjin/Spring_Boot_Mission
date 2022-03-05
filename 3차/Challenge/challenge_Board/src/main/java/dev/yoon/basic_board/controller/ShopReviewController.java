package dev.yoon.basic_board.controller;


import dev.yoon.basic_board.dto.shop.ShopPostDto;
import dev.yoon.basic_board.dto.shop.ShopReviewDto;
import dev.yoon.basic_board.service.ShopReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("shop-review")
@RequiredArgsConstructor
public class ShopReviewController {

    private final ShopReviewService shopReviewSerive;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShopReviewDto> creatShopPost(
            @RequestBody ShopReviewDto shopReviewDto) {
        ShopReviewDto dto = this.shopReviewSerive.createShopReview(shopReviewDto);

        if(dto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dto);
    }


}
