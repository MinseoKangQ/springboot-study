package com.likelion.community.repository;

import com.likelion.community.model.BoardDto;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BoardRepository {
    BoardDto create(BoardDto dto);
    BoardDto read(Long id);
    Collection<BoardDto> readAll();
    boolean update(Long id, BoardDto dto);
    boolean delete(Long id);
}
