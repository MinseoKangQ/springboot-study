public class Student extends AbstractPerson {
    public Student(String name, int age) {
        super(name, age);
    }

    @Override
    public void speak() {
        System.out.println(String.format("Hi, my name is %s. I am a student", getName()));
    }
}
