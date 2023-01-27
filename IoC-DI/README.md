<h3>스프링의 핵심</h3>

<br>

- Spring

    - Spring 1.0 버전은 2004년 3월 출시

    - 지난 20년 동안 계속 자바 엔터프라이즈 어플리케이션 개발 최고의 자리를 차지

    - 스프링 프레임워크는 20여 가지로 구성(https://spring.io/projects/spring-framework)

    - 모듈들은 스프링의 핵심 기능(DI, AOP 등)을 제공하며, 필요한 모듈만 선택하여 사용 가능

    - 현재 단일 아키텍처(모놀리스) 마이크로서비스 아키텍처로 변환 중이며, 여기에 맞춰 스프링도 진화하고 있는 상태

    - 스프링 부트, 스프링 클라우드, 스프링 데이터, 스프링 배치, 스프링 시큐리티에 중점

<br>

- Spring 개발

    - Spring은 '테스트 용이성', '느슨한 결합'에 중점을 두고 개발

    - 2000년대 초 자바 EE 애플리케이션은 작성이 어려웠고, 한 번 테스트 하는 것이 번거로웠기에 느슨한 결합이 된 애플리케이션 개발이 힘든 상태였고, 데이터베이스와 같이 외부에 의존성을 두는 경우에는 단위테스트가 불가능 했음

<br>

- IoC의 등장 : 스프링이 다른 프레임워크와의 가장 큰 차이점이 IoC를 통한 개발 진행

<br>

- AOP : AOP를 사용하여 로깅, 트랙잭션 관리, 시큐리티에서의 적용 등 AspectJ와 같이 완벽하게 구현된 AOP와 통합하여 사용 가능

<br>

- POJO

    - IoC/DI 의존 관계 주입

    - AOP 관점 중심 프로그램

    - PSA 이식 가능한 추상화

<br>

<b>참고</b>
<table>
<tr><td>스프링 부트</td><td>스프링 애플리케이션을 만들 때에 필요한 초기 설정을 간편하게 해주는 프레임워크</td></tr>
<tr><td>스프링 클라우드</td><td>MSA 구성을 지원하는 스프링 부트 기반의 프레임워크</td></tr>
<tr><td>스프링 데이터</td><td>DB와 관련된 많은 하위 프로젝트를 포함하는 프로젝트</td></tr>
<tr><td>스프링 배치</td><td>엔터프라이즈 시스템의 운영에 있어 대용량 일괄처리의 편의를 위해 설계된 가볍고 포괄적인 배치 프레임워크</td></tr>
<tr><td>스프링 시큐리티</td><td>스프링 기반의 애플리케이션의 보안(인증과 권한, 인가 등)을 담당하는 스프링 하위 프레임워크</td></tr>
</table>

<br>

- MSA : 마이크로 서비스 아키텍처(Micro Service Architecture)의 약자로 단일 프로그램을 각 컴포넌트 별로 나누어 작은 서비스의 조합으로 구축하는 방법

<br>

<hr>

<br>

<h3>IoC(Inversion of Control)</h3>

<br>

- 스프링에서는 일반적인 Java 객체를 new로 생성하여 개발자가 관리하는 것이 아님

- Spring Container에 모두 맡김

- 개발자에서 프레임워크로 제어 객체 관리의 권한이 넘어갔으므로 "제어의 역전"이라고 함

- Spring Container라는 공간에 우리가 사용하고자 하는 객체가 이미 만들어져 있고, 싱글톤 형태로 관리

- 모든 객체의 제어 권한은 Spring Container에 있음

<br>

<hr>

<br>

<h3>DI(Dependency Injection)</h3>

<br>

- 객체 사용을 위한 주입

- 의존성으로부터 격리시켜 코드 테스트에 용이

- DI로 불가능한 상황을 Mock과 같은 기술을 통해 안정적인 테스트가 가능

- 코드 확정하거나 변경할 때 영향을 최소화(추상화)

- 순환참조 막을 수 있음

<br>

```java
// IoCApplication.java
@SpringBootApplication
public class IoCApplication {

	public static void main(String[] args) {

		SpringApplication.run(IoCApplication.class, args);

		ApplicationContext context = ApplicationContextProvider.getContext();

		Encoder encoder = context.getBean("urlEncode", Encoder.class);
		String url = "www.naver.com/books/it?page=10&size=20&name=spring-boot";

		String result = encoder.encode(url);
		System.out.println(result);

	}

}

@Configuration // 한 개의 클래스에서 여러 개의 Bean을 등록할 것이라고 명시
class AppConfig {

	@Bean("base64Encode")
	public Encoder encoder(Base64Encoder base64Encoder) {
		return new Encoder(base64Encoder);
	}

	@Bean("urlEncode")
	public Encoder encoder(UrlEncoder urlEncoder) {
		return new Encoder(urlEncoder);
	}

}
```

<br>

```java
// ApplicationContextProvider.java
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

}
````

<br>

```java
// IEncoder 인터페이스
public interface IEncoder {
    String encode(String message);
}
```

<br>

```java
// Encoder.java
@Component
public class Encoder {

    private IEncoder iEncoder;

    public Encoder(@Qualifier("base74Encoder") IEncoder iEncoder) {
        this.iEncoder = iEncoder;
    }

    public void setIEncoder(IEncoder iEncoder) {
        this.iEncoder = iEncoder;
    }

    public String encode(String message) {
        return iEncoder.encode(message);
    }

}
```

<br>

```java
// Base64Encoder.java
@Component("base74Encoder")
public class Base64Encoder implements IEncoder {

    @Override
    public String encode(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes());
    }

}
```

<br>

```java
// UrlEncoder.java
@Component
public class UrlEncoder implements IEncoder{

    @Override
    public String encode(String message) {
        try {
            return URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
```