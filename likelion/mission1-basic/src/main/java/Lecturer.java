public class Lecturer extends AbstractPerson{

    private String name;
    private int age;

    public Lecturer(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void speak() {
        System.out.println("I'm a lecturer");
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
