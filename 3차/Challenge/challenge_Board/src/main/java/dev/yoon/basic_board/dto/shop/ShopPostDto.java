package dev.yoon.basic_board.dto.shop;

import dev.yoon.basic_board.domain.ShopPost;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
public class ShopPostDto {

    @NotNull
    private Long userId;

    private Long shopId;

    @Size(min = 3, max = 10, message = "size between 3 - 10")
    private String title;

    @Size(max = 40, message = "size under 40")
    private String content;

    public ShopPostDto(ShopPost shopPost) {
        this.userId = shopPost.getUser().getId();
        this.shopId = shopPost.getShop().getId();
        this.title = shopPost.getTitle();
        this.content = shopPost.getContent();

    }
}
