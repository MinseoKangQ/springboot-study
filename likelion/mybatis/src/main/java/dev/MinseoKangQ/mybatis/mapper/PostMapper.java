package dev.MinseoKangQ.mybatis.mapper;

import dev.MinseoKangQ.mybatis.dto.PostDto;

import java.util.List;

public interface PostMapper {
    int createPost(PostDto dto);
    int createPostAll(List<PostDto> dtoList);
    PostDto readPost(int id);
    List<PostDto> readPostAll();
    PostDto readPostQuery(PostDto dto);
    int updatePost(PostDto dto);
    int deletePost(int id);
}
