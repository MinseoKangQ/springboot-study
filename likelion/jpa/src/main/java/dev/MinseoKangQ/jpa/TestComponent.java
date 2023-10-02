package dev.MinseoKangQ.jpa;

import dev.MinseoKangQ.jpa.entity.BoardEntity;
import dev.MinseoKangQ.jpa.entity.PostEntity;
import dev.MinseoKangQ.jpa.repository.BoardRepository;
import dev.MinseoKangQ.jpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestComponent {
    public TestComponent(
            @Autowired BoardRepository boardRepository,
            @Autowired PostRepository postRepository
            ) {

        // BoardEntity 기본 테스트
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setName("new board");
        // 결과물은 새로운 Entity 객체로 받아야 함
        BoardEntity newBoardEntity = boardRepository.save(boardEntity);
        System.out.println(newBoardEntity.getName());

        // PostEntity 기본 테스트
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle("hello ORM");
        postEntity.setContent("This Entity is created by hibernate!");
        postEntity.setWriter("MinseoKangQ");
        postEntity.setBoardEntity(newBoardEntity);
        PostEntity newPostEntity = postRepository.save(postEntity);

        // PostRepository의 findAllByWriter 테스트
        System.out.println(postRepository.findAllByWriter("MinseoKangQ").size());
    }
}
