package dev.yoon.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.yoon.board.domain.Media;
import dev.yoon.board.domain.Post;
import dev.yoon.board.domain.QPost;
import dev.yoon.board.dto.PostDto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static dev.yoon.board.domain.QPost.post;

@Repository
public class PostRepositoryImpl implements PostRepository {

    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory query;

    public PostRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public void save(Post post) {
        em.persist(post);
    }

    @Override
    public Post findPostOnebyBoardId(Long boardId, Long postId) {
        return query
                .select(post)
                .from(post)
                .join(post.board)
                .fetchJoin()
                .where(post.id.eq(postId), post.board.id.eq(boardId))
                .fetchOne();
    }

//    @Override
//    public boolean updatePost(Long boardId, Long postId, PostDto postDto) {
//
//        Post result = query
//                .select(post)
//                .from(post)
//                .join(post.board)
//                .fetchJoin()
//                .where(post.id.eq(postId), post.board.id.eq(boardId))
//                .fetchOne();
////        List<Post> postList = em.createQuery("select p from Post p where p.id =:postId AND p.board.id = :boardId", Post.class)
////                .setParameter("postId",postId)
////                .setParameter("boardId", boardId)
////                .getResultList();
//        if(result == null) {
//            return false;
//        }
//        result.update(postDto);
//        return true;
//    }

    @Override
    public boolean deletePost(Long boardId, Long postId, String pw) {

        Post result = query
                .select(post)
                .from(post)
                .join(post.board)
                .fetchJoin()
                .where(QPost.post.id.eq(postId), QPost.post.board.id.eq(boardId))
                .fetchOne();

        if(result == null) {
            return false;
        }

        if (result == null || !result.getPw().equals(pw)) {
            return false;
        }

        em.remove(result);
        return true;
    }



//    @Override
//    public List<File> findAllByPost(Long id) {
//        return em.createQuery("select f from File f where f.post.id=:id", File.class)
//                .setParameter("id", id)
//                .getResultList();
//    }

    @Override
    public List<Post> findPostAllByBoardId(Long boardId) {

        return query
                .select(post)
                .from(post)
                .join(post.board)
                .fetchJoin()
                .where(post.board.id.eq(boardId))
                .fetch();
    }
}
