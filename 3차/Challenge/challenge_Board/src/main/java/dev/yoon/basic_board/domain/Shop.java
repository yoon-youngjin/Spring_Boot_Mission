package dev.yoon.basic_board.domain;


import dev.yoon.basic_board.dto.shop.ShopDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "SHOP")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<ShopPost> shopPosts;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<ShopReview> shopReviews;


    public Shop(ShopDto shopDto) {
        this.category = shopDto.getCategory();
        this.address = shopDto.getAddress();
    }

    public void addShopPost(ShopPost shopPost) {
        this.shopPosts.add(shopPost);
        shopPost.setShop(this);
    }


    public void addShopReview(ShopReview shopReview) {
        this.shopReviews.add(shopReview);
        shopReview.setShop(this);
    }

    public void update(ShopDto shopDto) {
        this.category = shopDto.getCategory();
        this.address = shopDto.getAddress();
    }
}
