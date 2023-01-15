package com.example.hello.controller;

import com.example.hello.dto.UserRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/get")
public class GetApiController {

    // 1)
    @GetMapping(path = "/hello") // http://localhost:9090/api/get/hello
    public String hello() {
        return "hello";
    }

    // 2)
    // 예전에 사용하던 방식
    // @RequestMapping("/hi") // get/post/put/delete 모든 메서드가 맵핑됨
    @RequestMapping(path = "/hi", method = RequestMethod.GET) // get만 동작 http://localhost:9090/api/get/hi
    public String hi() {
        return "hi";
    }

    // 3
    // http://localhost:9090/api/get 까지는 고정
    // http://localhost:9090/api/get/path-variable/{name}
//    @GetMapping("/path-variable/{name}")
//    public String pathVariable(@PathVariable String name) {
//        System.out.println("PathVariable : " + name);
//        return name;
//    }

    // 3
    @GetMapping("/path-variable/{name}")
    public String pathVariable(@PathVariable(name = "name") String pathName, String name) {
        System.out.println("PathVariable : " + pathName);
        return pathName;
    }

    // search?q = IntelliJ
    // &sxsrf=AJOqlzXobLa9cV_bu_e75RcNVVCDBS8hSQ%3A1673771542326
    // &ei=FrrDY_fME5bChwPSqafgCQ
    // &ved=0ahUKEwj3r9zxlMn8AhUW4WEKHdLUCZwQ4dUDCA8&uact=5
    // &oq=IntelliJ&gs_lcp=Cgxnd3M~

    // ?key=value&key2=value2

    // 4) Query Param으로 받는 첫 번째 방법
    // http://localhost:9090/api/get/query-param?user=steve&email=steve@gamil.com&age=30
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

    // 5) Query Param으로 받는 두 번째 방법
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

    // 6) Query Param으로 받는 세 번째 방법 - 현업에서 많이 사용 및 추천
    @GetMapping("/query-param03")
    public String queryParam03(UserRequest userRequest) {
        System.out.println(userRequest.getName());
        System.out.println(userRequest.getEmail());
        System.out.println(userRequest.getAge());
        return userRequest.toString();
    }

}
