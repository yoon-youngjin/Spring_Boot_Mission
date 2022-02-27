package dev.yoon.basic_board.repository;



import dev.yoon.basic_board.domain.Post;
import dev.yoon.basic_board.dto.PostDto;

import java.util.List;

public interface PostRepository {

    void save(Post post);

    List<Post> findAll();

    List<Post> findPostAllbyBoardId(Long boardId);

    Post findPostOnebyBoardId(Long boardId, Long postId);

    Post findById(Long id);

    boolean updatePost(Long boardId, Long postId, PostDto postDto);

    boolean deletePost(Long boardId, Long postId,String pw);


}
