public class Lecturer extends AbstractPerson{
    public Lecturer(String name, int age) {
        super(name, age);
    }

    @Override
    public void speak() {
        System.out.println(String.format("Hi, my name is %s. I am a lecturer", getName()));
    }
}
