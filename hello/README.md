<h3>PUT METHOD</h3>

<br>

* PUT 메소드
    
    * 의미 : 리소스 생성, 추가

    * CRUD : C / U

    * 멱등성 : O
    
    * 안정성 : X
    
    * Path Variable : O

    * Query Parameter : O

    * Data Body : O

    * PUT은 POST와 큰 차이 없음

<br>

* Snake case와 Camel case를 일치시키는 다른 방법

    * 클래스 전체에 대해 같은 룰을 적용시킬 수 있음

        ```java
        @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
        ```
    
    * 클래스 위에 해당 annotation 선언 시 Object Mapper 모듈이 동작할 때 Snake case로 인식

    * <u><b>@JsonProperty</b></u>는 하나의 변수에 대해서만 적용
        ```java
        @JsonProperty("car_number")
        private String carNumber;
        ```

    * <u><b>@JsonNaming</b></u>은 해당 클래스의 모든 변수에 대해 적용
        ```java
        @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
        public class PostRequestDto { }
        ```

<br>

* 패키지 생성과 클래스 생성
  
  * [ put-api ] - [ src ] - [ main ] - [ java ] - [ com.example.put-api ] 내에 PutApiController 클래스 작성
  <br>
    ```java
    @RestController //  해당 Class를 REST API 처리하는 Controller로 등록
    @RequestMapping("/api") // URI를 지정해주는 Annotation
    public class PutApiController {  }
    ```
    
<br>

<b><u>PUT 메소드 작성</b></u>

<br>

1. @PutMapping("/주소")

    * dto 패키지 및 클래스 작성 및 사용 (클래스에 toString 오버라이딩)
    
    * [ put-api ] - [ src ] - [ main ] - [ java ] - [ com.example.put-api ] - [ dto ] 패키지 생성

    ```java
    @PutMapping("/put")
    public PostRequestDto put(@RequestBody PostRequestDto requestDto) {
        System.out.println(requestDto);
        return requestDto;
    }
    ```

<br>

2. @PutMapping("/주소/PathVariable")

    * dto 패키지 및 클래스 작성 및 사용 (클래스에 toString 오버라이딩)
    
    * [ put-api ] - [ src ] - [ main ] - [ java ] - [ com.example.put-api ] - [ dto ] 패키지 생성

        ```java
        @PutMapping("/put/{userId}")
        public PostRequestDto putPathVariable(@RequestBody PostRequestDto requestDto, @PathVariable(name = "userId") Long id) {
            System.out.println(id);
            return requestDto;
        }
        ```

<br>

<b><u>정리</b></u>

<table>
    <tr><td>@RestController</td><td>Rest API 설정</td></tr>
    <tr><td>@RequestMapping</td><td>리소스를 설정(method로 구분 가능)</td></tr>
    <tr><td>@PutMapping</td><td>Put Resource 설정</td></tr>
    <tr><td>@RequestBody</td><td>Request Body 부분 Parsing</td></tr>
    <tr><td>@PathVariable</td><td>URL Path Variable Parsing</td></tr>
</table>
