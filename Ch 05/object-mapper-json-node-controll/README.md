<h3>01. Object Mapper 개념</h3>

<br>

Obejct Mapper
- 스프링 프레임워크에서 사용하는 자바에 대한 라이브러리
- 반드시 스프링에서 사용되는 것은 아님

<br>

---

<br>

<h3>02. 프로젝트 기본 설정</h3>

<br>

프로젝트 기본 설정
- 구글에 Maven Repository 검색(https://mvnrepository.com/)
- 위 사이트에서 object mapper 검색 후 Jackson Databind 선택
- 2.12.1, Gradle 선택 후 프로젝트 build.gradle dependencies에 추가
- Object Mapper는 UTF-8이 기본이므로 설정이 안되어 있다면 반드시 인코딩은 UTF-8로 해야함 (맥은 기본으로 UTF-8이 되어있지만, 윈도우는 기본으로 MS임)

<br>

프로젝트에서 출력한 값 Json 포맷인지 확인하기
- 출력 후 구글에 Json Validator 검색(https://jsonformatter.curiousconcept.com/)
- 위 사이트에서 프로젝트에서 출력한 값 붙여넣고 Json 포맷인지 확인하기

<br>

실습 내용
- Object Mapper로 각각의 Json Node 자체를 컨트롤 하기
- 우리가 원하는 형태로 Json 변형 가능

<br>

---

<br>

<h3>03. Object Mapper 실무 사례</h3>

<br>

dto 패키지 작성 및 클래스 작성(User, Car)

- Car는 List로 받음

```java
// User.java
package dto;

import java.util.List;

public class User {

    private String name;
    private int age;
    private List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", cars=" + cars +
                '}';
    }

}
```

<br>

```java
// Car.java
package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Car {

    private String name;

    // snake case, camel case 통일(by @JsonProperty)
    @JsonProperty("car_number")
    private String carNumber;

    @JsonProperty("TYPE")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

```

<br>

```java
// Main.java
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.Car;
import dto.User;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        User user = new User();
        user.setName("홍길동");
        user.setAge(10);

        Car car1 = new Car();
        car1.setName("K5");
        car1.setCarNumber("11가 1111");
        car1.setType("sedan");

        Car car2 = new Car();
        car2.setName("Q5");
        car2.setCarNumber("22가 2222");
        car2.setType("SUV");

        List<Car> carList = Arrays.asList(car1, car2);
        user.setCars(carList);

        // user를 String 타입으로 변환
        String json = objectMapper.writeValueAsString(user);
        System.out.println("--- user를 String 타입으로 변환 ---");
        System.out.println(json);
        System.out.println();

        // String json 값을 Json 형태로 바꿔서
        JsonNode jsonNode = objectMapper.readTree(json);

        // 하나씩 Parsing
        String _name = jsonNode.get("name").asText(); // name이라는 field로 가져오기
        int _age = jsonNode.get("age").asInt();
        System.out.println("--- Json 형태로 바꾸고 하나씩 Parsing ---");
        System.out.println("name : " + _name);
        System.out.println("age : " + _age);
        System.out.println();

        // 배열 node 표현 및 가져오기(구조를 알고 있는 경우)
        JsonNode cars = jsonNode.get("cars");
        ArrayNode arrayNode = (ArrayNode)cars;

        // 원하는 타입으로 바꾸기
        // object 넣고, 우리가 원하는 타입 넣어서 Mapping
        List<Car> _cars = objectMapper.convertValue(arrayNode, new TypeReference<List<Car>>() {});
        System.out.println("--- 배열 node 가져오기 ---");
        System.out.println(_cars);
        System.out.println();

        // put 메소드로 기존의 value 수정
        ObjectNode objectNode = (ObjectNode) jsonNode;
        objectNode.put("name", "steve");
        objectNode.put("age", 20);

        System.out.println("--- ObjectNode의 put 메소드로 기존 값 수정 ---");
        System.out.println(objectNode.toPrettyString());
        System.out.println();
    }
}

```