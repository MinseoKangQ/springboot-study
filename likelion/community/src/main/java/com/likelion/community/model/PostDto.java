package com.likelion.community.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long userId;
}
