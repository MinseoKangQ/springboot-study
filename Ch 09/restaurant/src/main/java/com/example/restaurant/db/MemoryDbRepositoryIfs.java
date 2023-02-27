package com.example.restaurant.db;

import java.util.List;
import java.util.Optional;

public interface MemoryDbRepositoryIfs<T> {
    Optional<T> findById(int index); // 해당 타입에 대해 Optional 하게 리턴
    T save(T entity); // 저장
    void deleteById(int index); // 삭제
    List<T> listAll(); // 전체 리스트를 리턴
}