<h3>GET METHOD</h3>

<br>

* GET 메소드
    
    * 의미 : 리소스 취득

    * CRUD : R

    * 멱등성 : O
    
    * 안정성 : O
    
    * Path Variable : O

    * Query Parameter : O 

    * Data Body : X

<br>

* 패키지 생성과 클래스 생성
  
  * 요청을 받는 부분을 컨트롤러(controller)라고 함
  
  * [ hello ] - [ src ] - [ main ] - [ java ] - [ com.example.hello ] - [ controller ] 패키지 생성

  * 컨트롤러 패키지 내에 getApiController 클래스 작성
  <br>
    ```java
    @RestController //  해당 Class를 REST API 처리하는 Controller로 등록
    @RequestMapping("/api") // URI를 지정해주는 Annotation
    public class getApiController {  }
    ```
    
<br>

<b><u>GET 메소드 작성</u></b>

<br>

1. 명확하게 주소 명시

   * @GetMapping(path="/주소") 

    ```java
    @GetMapping(path = "/api") 
    public String hello() { return "hello"; }
    ```

<br>

2. 예전에 사용하던 방식

    * @RequestMapping("/주소")

    * 이 같은 방식을 사용하면 GET/POST/PUT/DELETE 모든 메서드가 맵핑 됨

    ```java
    @RequestMapping("/hello")
    public String hi() { return "hi"; }
    ```
    
    * GET만 동작하도록 메소드를 지정해야 함
   
    ```java
    @RequestMapping(path = "/hi", method = RequestMethod.GET)
    public String hi() { return "hi"; }
    ```
   
<br>

3. 변화하는 구간 작성

    * Path Variable로 받으면 됨
   
    * @GetMapping("/주소/{<u><b>변수이름</b></u>}")

    * 매개변수에 @PathVariable 작성
   
    * 변수이름과 변수명은 동일해야 함
   
    ```java
    @GetMapping("/path-variable/{name}")
    public String pathVariable(@PathVariable(name = "name") String pathName, String name) {
        System.out.println("PathVariable : " + pathName);
        return pathName;
    }
    ```
   
<br>

4. Query Parameter를 받는 첫 번째 방법

   * 매개변수로 Map 받기

   * @GetMapping(path = "/주소")

   * 매개변수에 @RequestParam 작성

   * key와 value 형태이므로 매개변수 Map

   ```java
    @GetMapping(path = "/query-param")
    public String queryParam(@RequestParam Map<String, String> queryParam) {

        StringBuilder sb = new StringBuilder();

        queryParam.entrySet().forEach( entry -> {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            System.out.println("\n");

            sb.append(entry.getKey() + " = " + entry.getValue() + "\n");
        });


        return sb.toString();
    }
    ```

<br>

5. Query Parameter를 받는 두 번째 방법

   * 각 매개변수에 @RequestParam 지정

   * @GetMapping("/주소")

   * 매개변수에 @RequestParam 작성

   * 매개변수가 많아지면 Annotation을 일일이 붙이는 데에 부담

    ```java
    @GetMapping("/query-param02")
    public String queryParam02(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam int age
    ) {
        System.out.println(name);
        System.out.println(email);
        System.out.println(age);
        return name + " " + email + " " + age;
    }
    ```

<br>

6. Query Parameter를 받는 세 번째 방법

   * dto 패키지 및 클래스 작성 및 사용 (클래스에 toString 오버라이딩)

   * [ hello ] - [ src ] - [ main ] - [ java ] - [ com.example.hello ] - [ dto ] 패키지 생성

   * dto 내 작성한 클래스를 매개변수로 받기

   * @GetMapping("/주소")
   
   * 매개변수에 dto클래스 변수명

   * 현업에서 가장 많이 사용하는 방식

    ```java
    @GetMapping("/query-param03")
    public String queryParam03(UserRequest userRequest) {
        System.out.println(userRequest.getName());
        System.out.println(userRequest.getEmail());
        System.out.println(userRequest.getAge());
        return userRequest.toString();
    }
    ```

<br>

<b><u>정리</b></u>

<table>
    <tr><td>@RestController</td><td>Rest API 설정</td></tr>
    <tr><td>@RequestMapping</td><td>리소스를 설정(method로 구분 가능)</td></tr>
    <tr><td>@GetMapping</td><td>Get Resource 설정</td></tr>
    <tr><td>@ReqeustParam</td><td>URL Query Param Parsing</td></tr>
    <tr><td>@PathVariable</td><td>URL Path Variable Parsing</td></tr>
    <tr><td>Object</td><td>Query Param Object로 Parsing</td></tr>
    
</table>

<br>

* 참고

    * 멱등성이란 여러 번 요청을 해도 같은 응답이 오는 것

    * DTO(= Data Transfer Object)란 데이터를 오브젝트로 변환하는 객체, 로직을 가지지 않는 데이터 객체

    * URL에서, "?" 이후가 Query Parameter
  
    * "&" 기준으로 잘라보면 "key=value" 형태