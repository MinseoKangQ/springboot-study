<h3>Object Mapper</h3>

<br>

* 코드는 Test 코드로 작성

<br>

* Object Mapper : Json(Text) 형태를 Object로 바꿔줌, 혹은 그 반대
    * Controller에서 response를 Object로 받으면 Json(Text)로 바꿔줌
    * Controller에서 request를 Json(Text)로 받으면 Object로 바꿔줌

<br>

* 직접 Object Mapper를 객체로 생성해서 활용
```java
var objectMapper = new ObjectMapper();
```

<br>

1) Object -> Json(Text)

- getter 메서드 참조함
- Dto 클래스에 <b><u>get 메서드</u></b> 있어야 함
- Dto 클래스의 멤버 변수에 해당되지 않는 것에 get 붙이면 getter 메서드로 인식하기 때문에 메서드 작성 시 유의

```java
var user = new User("steve", 10, "010-1111-2222");
var text = objectMapper.writeValueAsString(user);
System.out.println(text);
```
    
<br>

2) Json(Text) -> Object

- Dto 클래스에 디폴트 생성자 있어야 함
- objectMapper.readValue(Json(Text), 어떤 클래스 타입으로 바꿀 것인지)
    
```java
var objectUser = objectMapper.readValue(text, User.class);
System.out.println(objectUser);
```

<br>