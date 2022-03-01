package dev.yoon.board.repository;

import dev.yoon.board.domain.Media;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository {

    void save(Media media);

    void deleteMedia(Media media);


    Media findMediaOneFromPost(Long postId, Long mediaId);

    List<Media> findMediaAllFromPost(Long postId);
}
