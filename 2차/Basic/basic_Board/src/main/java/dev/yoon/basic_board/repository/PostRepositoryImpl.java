package dev.yoon.basic_board.repository;


import dev.yoon.basic_board.domain.Board;
import dev.yoon.basic_board.domain.Post;
import dev.yoon.basic_board.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class PostRepositoryImpl implements PostRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void save(Post post) {
        em.persist(post);
    }

    @Override
    public List<Post> findAll() {
        return em.createQuery("select b from Post b", Post.class).getResultList();
    }

    @Override
    public List<Post> findPostAllbyBoardId(Long boardId) {
        return em.createQuery("select p from Post p where p.board.id = :boardId", Post.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    @Override
    public Post findPostOnebyBoardId(Long boardId, Long postId) {
        List<Post> postList = em.createQuery("select p from Post p where p.id =:postId AND p.board.id = :boardId", Post.class)
                .setParameter("postId",postId)
                .setParameter("boardId", boardId)
                .getResultList();

        return postList.get(0);
    }

    @Override
    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    @Override
    public boolean updatePost(Long boardId, Long postId, PostDto postDto) {

        List<Post> postList = em.createQuery("select p from Post p where p.id =:postId AND p.board.id = :boardId", Post.class)
                .setParameter("postId",postId)
                .setParameter("boardId", boardId)
                .getResultList();

        if(postList.isEmpty()) {
            return false;
        }

        Post post = postList.get(0);
        post.update(postDto);
        return true;
    }

    @Override
    public boolean deletePost(Long boardId, Long postId, String pw) {

        List<Post> postList = em.createQuery("select p from Post p where p.id =:postId AND p.board.id = :boardId", Post.class)
                .setParameter("postId",postId)
                .setParameter("boardId", boardId)
                .getResultList();

        if(postList.isEmpty()) {
            return false;
        }

        Post post = postList.get(0);

        if (post == null || !post.getPw().equals(pw)) {
            return false;
        }

        em.remove(post);
        return true;


    }

}
