public class Main {

    public static void main(String[] args) {

        AbstractPerson st = new Student("kang", 13);
        System.out.println("--- st info ---");
        System.out.println(st);

        AbstractPerson lec = new Lecturer("kim", 20);
        System.out.println("--- lec info ---");
        System.out.println(lec);
    }
}
