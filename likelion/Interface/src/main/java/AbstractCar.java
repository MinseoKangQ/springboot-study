// 인터페이스가 공유하는 기능을 Abstract Class 로 만든다
public abstract class AbstractCar implements CarInterface{
    protected int velocity = 0;

    @Override
    public void brake() {
        if(this.velocity < 0) this.velocity = 0;
    }
}
