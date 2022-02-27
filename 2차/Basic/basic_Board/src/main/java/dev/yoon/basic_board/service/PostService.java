package dev.yoon.basic_board.service;

import dev.yoon.basic_board.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(Long boardId, PostDto postDto) throws Exception;

    List<PostDto> readPostAll();

    List<PostDto> readPostAllbyBoardId(Long id);

    PostDto readPostOne(Long id);

    boolean updatePost(Long boardId, Long postId, PostDto postDto);

    boolean deletePost(Long boardId, Long postId, PostDto postDto);

    PostDto readPostOneByBoardId(Long boardId, Long postId);
}
