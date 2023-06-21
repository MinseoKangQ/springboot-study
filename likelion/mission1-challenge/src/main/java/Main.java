import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Person studentKim = new Student("Kim", 26);
        Person studentLee = new Student("Lee", 28);
        Person studentPark = new Student("Park", 24);

        Person lecturer = new Lecturer("Kkk", 30);

        List<Person> everyone = new ArrayList<>();
        everyone.add(studentKim);
        everyone.add(studentLee);
        everyone.add(studentPark);

        printItems(everyone);
    }

    public static <T>void printItems(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        if(!iterator.hasNext()) {
            System.out.println("No Elements");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("idx\t\titem\n");
        for(int i = 0; iterator.hasNext(); i++) {
            T item = iterator.next();
            sb.append(
                    String.format("%d\t\t%s\n", i, item)
            );
        }
        System.out.println(sb);

    }
}
