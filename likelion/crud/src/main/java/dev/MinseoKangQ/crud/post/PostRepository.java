package dev.MinseoKangQ.crud.post;

import java.util.List;

public interface PostRepository {
    boolean save(PostDto dto);
    List<PostDto> findAll();
    PostDto findById(int id);
    boolean delete(int id);
    boolean update(int id, PostDto dto);
}
