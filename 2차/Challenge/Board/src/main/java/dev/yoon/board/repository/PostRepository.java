package dev.yoon.board.repository;

import dev.yoon.board.domain.Media;
import dev.yoon.board.domain.Post;
import dev.yoon.board.dto.PostDto;

import java.util.List;

public interface PostRepository {

    void save(Post post);

    Post findPostOnebyBoardId(Long boardId, Long postId);


    List<Post> findPostAllByBoardId(Long boardId);


//    boolean updatePost(Long boardId, Long postId, PostDto postDto);

    boolean deletePost(Long boardId, Long postId, String pw);

}
