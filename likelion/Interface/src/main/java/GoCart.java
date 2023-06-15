public class GoCart extends AbstractCar implements CarInterface{

    private Driver driver;

    public GoCart() {
    }

    @Override
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void accelerate() {
        this.velocity += 5;
    }

    @Override
    public void brake() {
        this.velocity -= 5;
        super.brake();
    }
}
