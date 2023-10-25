package com.likelion.community.service;

import com.likelion.community.jpa.entity.BoardEntity;
import com.likelion.community.jpa.entity.PostEntity;
import com.likelion.community.jpa.entity.UserEntity;
import com.likelion.community.model.PostDto;
import com.likelion.community.repository.BoardRepository;
import com.likelion.community.repository.PostRepository;
import com.likelion.community.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JpaPostService implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;


    @Override
    public PostDto create(Long boardId, PostDto postDto) {
        if (!this.boardRepository.existsById(boardId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!this.userRepository.existsById(postDto.getUserId())) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        BoardEntity boardEntity = this.boardRepository.findById(boardId).get();
        UserEntity userEntity = this.userRepository.findById(postDto.getUserId()).get();
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postDto.getTitle());
        postEntity.setContent(postDto.getContent());
        postEntity.setWriter(userEntity);
        postEntity.setBoardEntity(boardEntity);
        postEntity = this.postRepository.save(postEntity);

        return new PostDto(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getWriter().getId()
        );
    }

    @Override
    public PostDto read(Long boardId, Long postId) {
        if(!this.postRepository.existsById(postId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        PostEntity postEntity = this.postRepository.findById(postId).get();
        if(!postEntity.getBoardEntity().getId().equals(boardId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return new PostDto(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getWriter().getId()
        );
    }

    @Override
    public Collection<PostDto> readAll(Long boardId) {
        Optional<BoardEntity> boardEntityOptional = this.boardRepository.findById(boardId);
        if (boardEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        BoardEntity boardEntity = boardEntityOptional.get();
        List<PostDto> postDtoList = new ArrayList<>();
        boardEntity.getPostEntityList().forEach(postEntity -> postDtoList.add(new PostDto(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getWriter().getId()
        )));

        return postDtoList;
    }

    @Override
    public boolean update(Long boardId, Long postId, PostDto dto) {
        if (!this.postRepository.existsById(postId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        PostEntity postEntity = this.postRepository.findById(postId).get();
        if(!postEntity.getBoardEntity().getId().equals(boardId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if(!postEntity.getWriter().getId().equals(dto.getUserId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        postEntity.setTitle(
                dto.getTitle() == null ? postEntity.getTitle() : dto.getTitle());
        postEntity.setContent(
                dto.getContent() == null ? postEntity.getContent() : dto.getContent());
        this.postRepository.save(postEntity);
        return true;
    }

    @Override
    public boolean delete(Long boardId, Long postId, String password) {
        if (!this.postRepository.existsById(postId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        PostEntity postEntity = this.postRepository.findById(postId).get();
        if (!postEntity.getBoardEntity().getId().equals(boardId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        this.postRepository.deleteById(postId);
        return true;

    }
}
