package dev.MinseoKangQ.jpa.repository;

import dev.MinseoKangQ.jpa.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {
}
