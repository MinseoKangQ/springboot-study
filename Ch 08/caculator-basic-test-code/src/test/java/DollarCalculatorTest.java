import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DollarCalculatorTest {

    @Mock
    public MarketApi marketApi;

    @BeforeEach
    public void init() {
        System.out.println("--- init() ---");
        Mockito.lenient().when(marketApi.connect()).thenReturn(3000);
    }

    @Test
    public void testHello() {
        System.out.println("--- testHello() ---");
        System.out.println("hello");
        System.out.println();
    }

    @Test
    public void test() {
        System.out.println("--- test() ---");
        MarketApi marketApi = new MarketApi();
        DollarCalculator dollarCalculator = new DollarCalculator(marketApi);
        dollarCalculator.init();
        Calculator calculator = new Calculator(dollarCalculator);

        Assertions.assertEquals(22000, calculator.sum(10,10));

        System.out.println(calculator.sum(10, 10));
        System.out.println();
    }

    @Test
    public void mockTest() {
        System.out.println("--- mockTest() ---");
        DollarCalculator dollarCalculator = new DollarCalculator(marketApi);
        dollarCalculator.init();
        Calculator calculator = new Calculator(dollarCalculator);

        Assertions.assertEquals(60000, calculator.sum(10,10));

        System.out.println(calculator.sum(10, 10));
        System.out.println();
    }
}
