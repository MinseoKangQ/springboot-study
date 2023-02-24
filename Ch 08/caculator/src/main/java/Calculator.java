public class Calculator {

    private ICalculator iCalculator; // 외부에서 주입받음

    public Calculator(ICalculator iCalculator) {
        this.iCalculator = iCalculator;
    }

    public int sum(int x, int y) {
        return this.iCalculator.sum(x,y);
    }

    public int minus(int x, int y) {
        return this.iCalculator.minus(x,y);
    }

}
