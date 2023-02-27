package com.example.restaurant.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract public class MemoryDbRepositoryAbstract<T extends MemoryDbEntity> implements MemoryDbRepositoryIfs<T>{

    private final List<T> db = new ArrayList<>(); // 리스트에 데이터를 넣는 형태로
    private int index = 0;

    @Override
    public Optional<T> findById(int index) {
        // getIndex를 통해서 MemoryDbEntity의 index에 접근 가능
        return db.stream().filter(it -> it.getIndex() == index).findFirst();
    }

    @Override
    public T save(T entity) {

        // 경우를 판단
        // 동일하면 이미 데이터가 있는 경우, 동일하지 않으면 데이터가 없는 경우
        var optionalEntity = db.stream().filter(it -> it.getIndex() == entity.getIndex()).findFirst();

        if(optionalEntity.isEmpty()) { // db에 데이터가 없는 경우 -> Create
            index++; // 인덱스 증가
            entity.setIndex(index); // 인덱스 세팅
            db.add(entity); // 저장
            return entity; // 리턴
        }

        else { // db에 이미 데이터가 있는 경우 -> Update
            var preIndex = optionalEntity.get().getIndex(); // 이미 있던 데이터의 인덱스 가져옴
            entity.setIndex(preIndex); // 새로운 데이터에 인덱스 세팅
            deleteById(preIndex); // 이전의 데이터 삭제
            db.add(entity); // 새로 받은 entity 넣기
            return entity;
        }
    }

    @Override
    public void deleteById(int index) {
        var optionalEntity = db.stream().filter(it -> it.getIndex() == index).findFirst();
        if(optionalEntity.isPresent()) { // 데이터가 이미 있는 경우
            db.remove(optionalEntity.get()); // 삭제
        }
    }

    @Override
    public List<T> listAll() {
        return db; // 데이터베이스에 있는 모든 것을 리턴
    }
}
