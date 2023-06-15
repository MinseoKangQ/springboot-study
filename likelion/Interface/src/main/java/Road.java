import java.util.ArrayList;
import java.util.List;

public class Road {
    private List<CarInterface> carsOnRoad;

    public Road(List<CarInterface> carsOnRoad) {
        this.carsOnRoad = carsOnRoad;
    }

    public Road() {
        carsOnRoad = new ArrayList<>();
    }

    public void addCar(CarInterface car) {
        this.carsOnRoad.add(car);
    }
}
