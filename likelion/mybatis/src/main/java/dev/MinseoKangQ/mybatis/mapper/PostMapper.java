package dev.MinseoKangQ.mybatis.mapper;

import dev.MinseoKangQ.mybatis.dto.PostDto;

import java.util.List;

public interface PostMapper {
    int createPost(PostDto dto);
    PostDto readPost(int id);
    List<PostDto> readPostAll();
    int updatePost(PostDto dto);
    int deletePost(int id);
}
