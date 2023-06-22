package dev.MinseoKangQ.mybatis.dao;

import dev.MinseoKangQ.mybatis.dto.PostDto;
import dev.MinseoKangQ.mybatis.mapper.PostMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostDao {
    private final SqlSessionFactory sessionFactory;

    public PostDao(@Autowired SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // C
    public int createPost(PostDto dto) {
        /* 작성방법 1
        // 데이터베이스와 통신을 하기 위해 세션 열어야 함
        SqlSession session = sessionFactory.openSession();
        // PostMapper 를 구현한 구현체가 주입됨
        PostMapper mapper = session.getMapper(PostMapper.class);
        int rowAffected = mapper.createPost(dto); // 데이터베이스와 통신 완료되는 부분
        session.close(); // 통신 끝나면 세션 낭비하지 않기 위해 세션 닫아야 함
        return rowAffected;
         */
        // 작성방법 2 (try-resource)
        try (SqlSession session = sessionFactory.openSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.createPost(dto);
        } // 세션이 자동으로 close 됨

    }

    // R
    public PostDto readPost(int id) {
        try (SqlSession session = sessionFactory.openSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.readPost(id);
        }
    }

    public List<PostDto> readPostAll() {
        try (SqlSession session = sessionFactory.openSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.readPostAll();
        }
    }

    // U
    public int updatePost(PostDto dto) {
        try (SqlSession session = sessionFactory.openSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.updatePost(dto);
        }
    }

    // D
    public int deletePost(int id) {
        try (SqlSession session = sessionFactory.openSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);
            return mapper.deletePost(id);
        }
    }
}