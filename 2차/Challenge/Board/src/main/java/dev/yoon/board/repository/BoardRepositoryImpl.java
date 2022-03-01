package dev.yoon.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.yoon.board.domain.Board;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static dev.yoon.board.domain.QBoard.*;

@Repository
public class BoardRepositoryImpl implements BoardRepository {

    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory query;

    public BoardRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public void save(Board board) {
        em.persist(board);
    }

    @Override
    public List<Board> findAll() {
        return query.select(board)
                .from(board)
                .fetch();
    }

    @Override
    public Board findOne(Long id) {
        return query
                .select(board)
                .from(board)
                .where(board.id.eq(id))
                .fetchOne();

    }


    @Override
    public void delete(Board board) {

        em.remove(board);
    }


}
