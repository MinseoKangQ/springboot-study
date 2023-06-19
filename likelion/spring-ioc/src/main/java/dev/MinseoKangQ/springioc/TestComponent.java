package dev.MinseoKangQ.springioc;

import org.springframework.stereotype.Component;

@Component
public class TestComponent {

    private TestInterface testInterface;

    // 여기서 주입
    public TestComponent(TestInterface testInterface, int gradeBean) {
        this.testInterface = testInterface;
        System.out.println(gradeBean);
    }

    public void sayHello() {
        this.testInterface.sayHello();
    }
}
