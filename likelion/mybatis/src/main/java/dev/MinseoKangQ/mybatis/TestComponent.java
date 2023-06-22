package dev.MinseoKangQ.mybatis;

import dev.MinseoKangQ.mybatis.dao.PostDao;
import dev.MinseoKangQ.mybatis.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestComponent {
    private final PostDao postDao;
    public TestComponent(@Autowired PostDao postDao){
        this.postDao = postDao;

        // Post 생성
        PostDto newPost = new PostDto();
        newPost.setTitle("From Mybatis");
        newPost.setContent("Hello Database!");
        newPost.setWriter("MinseoKangQ");
        newPost.setBoard(1);
        this.postDao.createPost(newPost);
        System.out.println("--- createPost 실행완료 ---");

        // Post 읽기
        List<PostDto> postDtoList = this.postDao.readPostAll();
        System.out.println("--- readPostAll 실행완료 ---");
        System.out.println(postDtoList.get(postDtoList.size() -1));

        // Post 수정
        PostDto firstPost = postDtoList.get(0);
        firstPost.setContent("Update From Mybatis");
        postDao.updatePost(firstPost);
        System.out.println("--- readPost 실행완료 ---");
        System.out.println(this.postDao.readPost(firstPost.getId()));
    }
}
