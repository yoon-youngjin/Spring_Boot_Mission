package dev.yoon.basic_board.domain;

import dev.yoon.basic_board.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "community_user")
// User라는 명칭이 DB상에 예약어로 존재하므로 테이블명을 분명하게 명시하는것을 추천
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area")
    private Area area;

    private Boolean verify;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            targetEntity = Post.class)
    private List<Post> postList = new ArrayList<>();


    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            targetEntity = Shop.class)
    private List<Shop> shopList;

    @OneToMany(
            mappedBy = "user",
            targetEntity = ShopPost.class)
    private List<ShopPost> shopPosts;

    @OneToMany(
            mappedBy = "user",
            targetEntity = ShopReview.class)
    private List<ShopReview> shopReviews;

    public User(UserDto userDto) {
        this.name = userDto.getName();
        this.verify = userDto.getVerify();
    }

    public void update(UserDto userDto) {
        this.name = userDto.getName();
    }

    public void addShop(Shop shop) {
        shopList.add(shop);
        shop.setUser(this);
    }

    public void addPost(Post post) {
        postList.add(post);
        post.setUser(this);
    }

    public void addShopPost(ShopPost shopPost) {
        shopPosts.add(shopPost);
        shopPost.setUser(this);
    }

    public void addShopReview(ShopReview shopReview) {
        shopReviews.add(shopReview);
        shopReview.setUser(this);
    }

    public void addArea(Area area) {
        this.area = area;
    }
}
