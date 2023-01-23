<h3>POST METHOD</h3>

<br>

* POST 메소드
    
    * 의미 : 리소스 생성, 추가

    * CRUD : C

    * 멱등성 : X
    
    * 안정성 : X
    
    * Path Variable : O

    * Query Parameter : △

    * Data Body : O

<br>

* POST 메소드 사전지식

    * 데이터를 주고 받을 때, 주로 xml이나 Json 형태 사용

        ```json
        {
            "key" : "value"
        }
        ```

    * 데이터타입 : string, number, boolean, object {}, array []

    * Json 사용 규칙 - Snake case / Camel case

        * Snake case : phone_number (Json에서 주로 사용)

        * Camel case : phoneNumber

    * Json 사용 예시

        ```json
        {
	        "phone_number" : "010-1111-2222",
	        "age" : 10,
            "isAgree" : false,
            "account" : {
            "email" : "steve@gmail.com",
            "password" : "1234"
	        }
        }
        ```
         ```json
        {
	        "user_list" : [
                {
                    "account" : "abcd",
                    "password" : "1234"
                },
                {
                    "account" : "aaaa",
                    "password" : "1111"

                },
                {
                    "account" : "bbbb",
                    "password" : "2222"

                }
	        ]
        }

        ```

        ```json
        {
	        "account" : "abcd",
	        "password" : "1234"
        } 
        ```


<br>

* 패키지 생성과 클래스 생성
  
  * [ post-api ] - [ src ] - [ main ] - [ java ] - [ com.example.post-api ] - [ controller ] 패키지 생성

  * 컨트롤러 패키지 내에 PostApiController 클래스 작성
  <br>
    ```java
    @RestController //  해당 Class를 REST API 처리하는 Controller로 등록
    @RequestMapping("/api") // URI를 지정해주는 Annotation
    public class PostApiController {  }
    ```
    
<br>

<b><u>POST 메소드 작성</u></b>

<br>

1. 매개변수로 Map 받기

    * @PostMapping("/주소") 

    * 매개변수에 @RequestBody 작성

    ```java
    @PostMapping("/post")
    public void post(@RequestBody Map<String, Object> requestData) {
        requestData.forEach((key, value) -> {
            System.out.println("key : " + key);
            System.out.println("value : " + value);
        });
    }
    ```

<br>

2. dto 작성 및 Snake case와 Camel case 맵핑

    * dto 패키지 및 클래스 작성 및 사용 (클래스에 toString 오버라이딩)
    
    * [ get-api ] - [ src ] - [ main ] - [ java ] - [ com.example.get-api ] - [ dto ] 패키지 생성

    * @PostMapping("/주소")

    * 매개변수에 @RequestBody dto클래스 변수명

        ```java
       @PostMapping("/post-request-dto")
        public void postRequestDtoFun(@RequestBody PostRequestDto postRequestDto) {
            System.out.println(postRequestDto);
        }   
        ```

    * java에서는 Camel case로 작성, Json에서는 Snake case로 작성

    * dto 패키지 내 클래스에서 @JsonProperty를 이용해 이를 매칭해줌

        ```java
        @JsonProperty("phone_number")
        private String phoneNumber; // phone_number
        ```

<br>

<b><u>정리</b></u>

<table>
    <tr><td>@RestController</td><td>Rest API 설정</td></tr>
    <tr><td>@RequestMapping</td><td>리소스를 설정(method로 구분 가능)</td></tr>
    <tr><td>@PostMapping</td><td>Post Resource 설정</td></tr>
    <tr><td>@RequestBody</td><td>Request Body 부분 Parsing</td></tr>
    <tr><td>@PathVariable</td><td>URL Path Variable Parsing</td></tr>
    <tr><td>@JsonProperty</td><td>json naming</td></tr>
    <tr><td>@JsonNaming</td><td>class json naming</td></tr>
</table>
