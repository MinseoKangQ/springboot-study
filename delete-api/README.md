<h3>DELETE METHOD</h3>

<br>

* DELETE 메소드
    
    * 의미 : 리소스 삭제

    * CRUD : D

    * 멱등성 : O
    
    * 안정성 : X
    
    * Path Variable : O

    * Query Parameter : O

    * Data Body : X

<br>

* DELETE 메소드 사전지식

    * 많은 값을 받지 않음

    * ex. DB index ID, user account ..

    * request가 틀리지 않는 이상 200 OK 던짐

    * 이미 리소스가 없는 상태여도 에러를 던지는 것이 아니라, 200 OK 값 던짐

    * Query Param이나 Path Variable 사용 권장 

<br>

* 패키지 생성과 클래스 생성
  
  * [ delete-api ] - [ src ] - [ main ] - [ java ] - [ com.example.delete-api ] 내에 DeleteApiController 클래스 작성
  <br>
    ```java
    @RestController //  해당 Class를 REST API 처리하는 Controller로 등록
    @RequestMapping("/api") // URI를 지정해주는 Annotation
    public class DeleteApiController {  }
    ```
    
<br>

<b><u>DELETE 메소드 작성</b></u>

<br>

@DeleteMapping("/주소/{pathVariable}")


```java
 @DeleteMapping("/delete/{userId}")
 public void delete(@PathVariable String userId, @RequestParam String account) {
     System.out.println(userId);
    System.out.println(account);
}
```

<br>

<b><u>정리</b></u>

<table>
    <tr><td>@RestController</td><td>Rest API 설정</td></tr>
    <tr><td>@RequestMapping</td><td>리소스를 설정(method로 구분 가능)</td></tr>
    <tr><td>@DeleteMapping</td><td>Delete Resource 설정</td></tr>
    <tr><td>@RequestParam</td><td>URL Query Param Parsing</td></tr>
    <tr><td>@PathVariable</td><td>URL Path Variable Parsing</td></tr>
    <tr><td>Object</td><td>Query Param Object로 Parsing</td></tr>
</table>