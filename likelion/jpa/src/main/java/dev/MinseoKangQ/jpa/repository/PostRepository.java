package dev.MinseoKangQ.jpa.repository;

import dev.MinseoKangQ.jpa.entity.BoardEntity;
import dev.MinseoKangQ.jpa.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<PostEntity, Long> {
    List<PostEntity> findAllByWriter(String writer); // where writer = ?
    List<PostEntity> findAllByWriterAndBoardEntity(String writer, BoardEntity boardEntity); // where writer = ? and board_entity_id = ?
    List<PostEntity> findAllByWriterContaining(String writer); // writer 의 내용을 포함하는 기준으로 검색
}
