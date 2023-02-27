<h3>01. 개념</h3>

<br>

TDD(Test-driven Development)
- 테스트 주도 개발에서 사용
- 코드의 유지 보수 및 운영 환경에서의 에러를 방지하기 위해서 단위 별로 검증하는 테스트 프레임워크

<br>

단위테스트
- 작성한 코드가 기대하는 대로 동작을 하는지 검증하는 절차

<br>

JUnit
- Java 기반의 단위 테스트를 위한 프레임워크
- Annotation 기반으로 테스트 지원
- Assert(예상, 실제)를 통해 검증

<br>

---

<br>

<h3>02. 프로젝트 구조 및 설정</h3>

<br>

프로젝트 구조

- ICalculator 인터페이스 : sum, minus 작성
- Calculator 클래스 : ICalculator를 외부에서 주입받음
- KrwCalculator 클래스 : ICalculator를 구현
- DollarCalculator 클래스 : 달러로 계산, MarketApi를 외부에서 주입받음
- MarketApi 클래스 : connect() 함수로 환율 가져옴
- DollarCalculator 클래스 : 테스트 코드 작성
<br>

빌드 도구 설정

<br>

build.gradle 파일 설정

```java
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.6.0'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine'
    testImplementation group: 'org.mockito', name: 'mockito-core', version: '3.9.0'
    testImplementation group: 'org.mockito', name: 'mockito-junit-jupiter', version: '3.9.0'
}

test {
    useJUnitPlatform()
}
```

<br>

---

<br>

<h3>03. main 패키지 코드 작성</h3>

<br>

```java
public interface ICalculator {

    int sum(int x, int y);
    int minus(int x, int y);
}
```

```java
public class Calculator {

    private ICalculator iCalculator; // 외부에서 주입받음

    public Calculator(ICalculator iCalculator) {
        this.iCalculator = iCalculator;
    }

    public int sum(int x, int y) {
        return this.iCalculator.sum(x,y);
    }

    public int minus(int x, int y) {
        return this.iCalculator.minus(x,y);
    }

}
```

```java
public class KrwCalculator implements ICalculator{

    private int price = 1;

    @Override
    public int sum(int x, int y) {
        x *= price;
        y *= price;
        return x+y;
    }

    @Override
    public int minus(int x, int y) {
        x *= price;
        y *= price;
        return x-y;
    }
}
```

```java
public class DollarCalculator implements ICalculator{

    private int price = 1;

    private MarketApi marketApi;

    public DollarCalculator(MarketApi marketApi) {
        this.marketApi = marketApi;
    }

    public void init() {
        this.price = marketApi.connect();
    }

    @Override
    public int sum(int x, int y) {
        x *= price;
        y *= price;
        return x+y;
    }

    @Override
    public int minus(int x, int y) {
        x *= price;
        y *= price;
        return x-y;
    }
}
```

```java
public class MarketApi {

    public int connect() {
        return 1100; // 환율 리턴
    }
}
```

<br>

---

<br>

<h3>04. test 패키지 코드 작성</h3>

<br>

일반 테스트 코드 작성

- @Test : 일반 테스트 코드 작성
- Assertions.assertEquals(기대값, 테스트값);

<br>

Mocking

- MarketApi가 항상 1100을 리턴하는 것이 아니라면 특정 객체가 메소드를 실행했을 때 우리가 원하는 결과값을 리턴시키도록 할 수 있음, 이를 Mocking 이라고 함
- 테스트 코드 클래스 위에 @ExtendWith(MockitoExtension.class) 작성하면 Mocking 준비
- 실행하려면 build.gradle에 Mockito 포함(02 참고) 
- maven repository에서 Mockito Core, Mockito JUnit Jupiter (둘 다 Gradle)을 포함시켜야 함
- 버전은 동일하게
- @Mock : Mocking 설정
- @BeforeEach : 테스트 실행되기 전에 실행

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DollarCalculatorTest {

    @Mock
    public MarketApi marketApi;

    @BeforeEach
    public void init() {
        System.out.println("--- init() ---");
        Mockito.lenient().when(marketApi.connect()).thenReturn(3000);
    }

    @Test
    public void testHello() {
        System.out.println("--- testHello() ---");
        System.out.println("hello");
        System.out.println();
    }

    @Test
    public void test() {
        System.out.println("--- test() ---");
        MarketApi marketApi = new MarketApi();
        DollarCalculator dollarCalculator = new DollarCalculator(marketApi);
        dollarCalculator.init();
        Calculator calculator = new Calculator(dollarCalculator);

        Assertions.assertEquals(22000, calculator.sum(10,10));

        System.out.println(calculator.sum(10, 10));
        System.out.println();
    }

    @Test
    public void mockTest() {
        System.out.println("--- mockTest() ---");
        DollarCalculator dollarCalculator = new DollarCalculator(marketApi);
        dollarCalculator.init();
        Calculator calculator = new Calculator(dollarCalculator);

        Assertions.assertEquals(60000, calculator.sum(10,10));

        System.out.println(calculator.sum(10, 10));
        System.out.println();
    }
}

```

<br>

---

<br>

<h3>05. 실행 결과 및 정리</h3>

<br>

실행 결과

<br>

정리

<table>
    <tr><td>@Test</td><td>테스트 코드</td></tr>
    <tr><td>Assertions.assertEquals(기대값, 테스트값);</td><td>테스트</td></tr>
    <tr><td>@ExtendWith(MockitoExtension.class)</td><td>Mocking 준비</td></tr>
    <tr><td>@Mock</td><td>Mocking 설정</td></tr>
    <tr><td>@BeforeEach</td><td>테스트 실행되기 전에 실행</td></tr>
</table>

