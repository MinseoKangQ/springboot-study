import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Driver unlicensed = new Driver("unlicensed", 0);
       Driver me = new Driver("me", 1);
       CarInterface car = new GoCart();
       car.setDriver(me);

       car = new SmallCar();
       car.setDriver(me);
//       car.setDriver(unlicensed);

        List<CarInterface> linkedList = new LinkedList<>();
        Road route88 = new Road(linkedList);
    }
}
