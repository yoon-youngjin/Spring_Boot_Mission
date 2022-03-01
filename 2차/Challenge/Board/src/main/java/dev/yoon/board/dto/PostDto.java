package dev.yoon.board.dto;

import dev.yoon.board.domain.Media;
import dev.yoon.board.domain.Post;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostDto {

    private Long boardId;

    private String boardName;

    private String title;

    private String content;

    private String writer;

    private String pw;

    private List<MediaDescriptorDto> files;

    public PostDto(Post post) {
        this.boardId = post.getBoard().getId();
        this.boardName = post.getBoard().getName();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.pw = "*****";
        List<Media> medias = post.getMedia();
        files = medias.stream()
                .map(media -> new MediaDescriptorDto(media))
                .collect(Collectors.toList());
    }

    public static PostDto createPostDtoPassWordMasked(Post post) {
        PostDto postDto = new PostDto();
        postDto.setBoardId(post.getBoard().getId());
        postDto.setBoardName(post.getBoard().getName());
        postDto.setTitle(post.getTitle());
        postDto.setWriter(post.getWriter());
        postDto.setPw("*****");

        postDto.setContent(post.getContent());
        return postDto;
    }

    public static PostDto createPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setBoardId(post.getBoard().getId());
        postDto.setTitle(post.getTitle());
        postDto.setWriter(post.getWriter());
        postDto.setPw(post.getPw());
        postDto.setContent(post.getContent());
        return postDto;
    }

}
