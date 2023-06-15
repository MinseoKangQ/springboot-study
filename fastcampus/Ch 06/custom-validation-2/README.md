<h3>01. 프로젝트 구조</h3>

<br>

ApiController 클래스의 내용은 이전 프로젝트와 동일하므로 코드는 생략


<br>

---

<br>

<h3>02. Car 클래스 작성</h3>

<br>

```java
package com.example.customvalidation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

public class Car {

    @NotBlank
    private String name;

    @NotBlank
    @JsonProperty("car_number")
    private String carNumber;

    @NotBlank
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

---

<br>

<h3>03. User 클래스 작성</h3>

<br>

```java
package com.example.customvalidation.dto;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class User {

    @NotBlank
    private String name;

    @Max(value = 90)
    private int age;

    @Valid
    private List<Car> cars;

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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
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

---

<br>

<h3>06. 실행 결과 및 정리</h3>

<br>

실행 결과

<br>

오실행 결과

<br>

정리
- Car 클래스에 validation 관련 어노테이션 작성
- 실제 Car를 사용하는 곳에 @Valid 붙여야 validation 실행