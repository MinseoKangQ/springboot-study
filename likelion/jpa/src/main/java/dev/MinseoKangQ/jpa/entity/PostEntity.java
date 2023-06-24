package dev.MinseoKangQ.jpa.entity;

import javax.persistence.*;

@Entity
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String writer;

    @ManyToOne(
            targetEntity = BoardEntity.class,
            fetch = FetchType.LAZY
    )
    private BoardEntity boardEntity;

    public PostEntity() {
    }


    public PostEntity(Long id, String title, String content, String writer, BoardEntity boardEntity) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.boardEntity = boardEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
