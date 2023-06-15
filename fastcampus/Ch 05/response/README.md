<h3>Response 내려주기</h3>

<br>

* Spring에서는 응답을 내려주는 다양한 방법들이 존재

* 요청을 받아야 응답을 내려줄 수 있기 때문에 Controller 작성

<br>

* 패키지 생성과 클래스 생성
  
  * [ response ] - [ src ] - [ main ] - [ java ] - [ com.example.response ] - [ controller ] 패키지 생성 

  * [ response ] - [ src ] - [ main ] - [ java ] - [ com.example.response ] - [ dto ] 패키지 생성

  * controller 패키지 안에 ApiController 클래스 작성

  * dto 패키지 안에 User 클래스 작성

  <br>

    ```java
    @RestController //  해당 Class를 REST API 처리하는 Controller로 등록
    @RequestMapping("/api") // URI를 지정해주는 Annotation
    public class ApiController {  }
    ```
    
<br>

<b><u>Response 작성 (기본)</u></b>

<br>

1. Text

   * @GetMapping으로 넘어온 쿼리 파라미터의 값을 리턴

    ```java
    @GetMapping("/text")
    public String text(@RequestParam String account) {
        return account;
    }
    ```

<br>

2. Json

    * @PostMapping이므로 @RequestBody 있어야 함

    * User 객체를 RequestBody로 받아서  User로 리턴

    * 거의 항상 200 OK Response

    * 동작 방식 : Request -> Object Manager -> Object -> Method -> Object Manager -> Json -> Response

    ```java
    @PostMapping("/json")
    public User json(@RequestBody User user) {
        return user;
    }
    ```

   
<br>

3. ResponseEntity

    * 가장 명료한 방법
   
    * PUT은 리소스 생성되면 201 Response

    * Response 내려줄 때 HttpStatus 정해줄 수 있음 -> ResponseEntity 객체 사용
   
    * ResponseEntity에 제네릭 타입

    * body(user) 부분 - Object Mapper를 통해 Json으로 바뀜

    * 응답에 대한 Customizing 필요할 때 ResponseEntity 사용

    ```java
    @PutMapping("/put")
    public ResponseEntity<User> put(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    ```
   
<br>

<b><u>HTML을 리턴하는 Controller</u></b>

* 파일 생성

    * controller 패키지 안에 PageController 클래스 작성

     ```java
    @Controller // html 리소스를 찾는 annotation
    public class PageController { }
     ```

     * [ response ] - [ src ] - [ main ] - [ resources ] - [ static ] 폴더에 main.html 작성

<br>

* PageController 

    * @Controller : String 리턴하면 html 리소스를 찾고 해당 리소스를 응답으로 내려줌

    * @ResponseBody : Response Body를 만들어서 응답 내려줌
    
        * user() 메소드 동작 확인 시 Talend API에서 age = 0으로 나옴

        * primitive int 타입의 디폴트 값은 0

        * null로 리턴받고 싶으면 Integer(Wrapper 타입)로 변수 선언 해야함

        * null 값 포함하지 않으려면 해당 dto 클래스에  @JsonInclude(JsonInclude.Include.NON_NULL) 작성
    
    <br>
    
    ```java
    @RequestMapping("/main")
    public String main() {
        return "main.html";
    } // 크롬에 http://localhost:8080/main 으로 확인 가능

    @ResponseBody
    @GetMapping("/user")
    public User user() {
        var user = new User(); // 타입 추론
        user.setName("steve");
        user.setAddress("패스트 캠퍼스");
        return user;
    }
    ```

