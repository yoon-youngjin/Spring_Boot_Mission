package dev.yoon.basic_board.dto.shop;

import dev.yoon.basic_board.domain.ShopReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
public class ShopReviewDto {

    @NotNull
    private Long userId;

    private Long shopId;

    @Size(min = 3, max = 10, message = "size between 3 - 10")
    private String title;

    @Size(max = 40, message = "size under 40")
    private String content;

    public ShopReviewDto(ShopReview shopReview) {
        this.userId = shopReview.getUser().getId();
        this.shopId = shopReview.getShop().getId();
        this.title = shopReview.getTitle();
        this.content = shopReview.getContent();

    }
}
