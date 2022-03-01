package dev.yoon.board.service;

import dev.yoon.board.dto.PostDto;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostDto createPost(Long boardId, PostDto postDto, List<MultipartFile> files) throws Exception;

    PostDto readPostOneByBoardId(Long boardId, Long postId);

    List<PostDto> readPostAllByBoardId(Long boardId);

    void updatePost(Long boardId, Long postId, PostDto postDto, List<MultipartFile> multipartFileList);

    boolean deletePost(Long boardId, Long postId, PostDto postDto);


}
