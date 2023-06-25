package dev.MinseoKangQ.jpa.repository;

import dev.MinseoKangQ.jpa.entity.BoardEntity;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<BoardEntity, Long> {
}
