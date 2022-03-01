package dev.yoon.board.repository;

import dev.yoon.board.domain.Media;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MediaRepositoryImpl implements MediaRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void save(Media media) {
        em.persist(media);
    }

    @Override
    public void deleteMedia(Media media) {
        em.remove(media);
    }

    @Override
    public Media findMediaOneFromPost(Long postId, Long mediaId) {
        return em.find(Media.class, mediaId);
    }

    @Override
    public List<Media> findMediaAllFromPost(Long postId) {
        return em.createQuery(
                        "select m from Media m" +
                                " where m.post.id = :postId")
                .setParameter("postId", postId)
                .getResultList();
    }


}
