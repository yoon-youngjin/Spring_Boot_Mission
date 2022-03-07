package dev.yoon.basic_board.dto;

import dev.yoon.basic_board.domain.Address;
import dev.yoon.basic_board.domain.Location;
import dev.yoon.basic_board.domain.Post;
import dev.yoon.basic_board.domain.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
public class UserDto {

    @NotNull
    @Size(max = 5, message = "size under 5")
    private String name;

    @NotNull
    private Boolean verify;

    private List<PostDto> postDtos;

    @NotNull
    private Address address;

    @NotNull
    private Location location;

    public UserDto(User user, List<Post> posts) {
        this.name = user.getName();
        this.verify = user.getVerify();
        this.address = user.getArea().getAddress();
        this.location = user.getArea().getLocation();

        this.postDtos = posts.stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());
    }
}
