package dev.yoon.basic_board.dto;

import dev.yoon.basic_board.domain.Post;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostDto {

    @NotNull
    private Long userId;

    private Long boardId;

    private String boardName;

    @NotNull
    @Size(min = 3, max = 10, message = "size between 3 - 10")
    private String title;

    @NotNull
    @Size(max = 40, message = "size under 40")
    private String content;

    @NotNull
    @Size(min = 3, max = 10, message = "size between 3 - 10")
    private String writer;

    @NotNull
    private String pw;


    public PostDto(Post post) {
        this.userId = post.getUser().getId();
        this.boardId = post.getBoard().getId(); // LAZY
        this.boardName = post.getBoard().getName(); // LAZY
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.pw = "*****";
    }

    public static PostDto createPostDtoPassWordMasked(Post post) {
        PostDto postDto = new PostDto();
        postDto.setUserId(post.getUser().getId());
        postDto.setBoardId(post.getBoard().getId());
        postDto.setBoardName(post.getBoard().getName());
        postDto.setTitle(post.getTitle());
        postDto.setWriter(post.getWriter());
        postDto.setPw("*****");
        postDto.setContent(post.getContent());
        return postDto;
    }


}
