package dev.MinseoKangQ.mybatis.mapper;

import dev.MinseoKangQ.mybatis.dto.BoardDto;

public interface BoardMapper {
    int createBoard(BoardDto dto);
}
