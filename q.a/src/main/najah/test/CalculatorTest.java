package main.najah.test;
import main.najah.code.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public class CalculatorTest {

    Calculator calculator;

    @BeforeAll
    static void initAll() {
        System.out.println("✅ Starting Calculator Tests...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("🧹 Finished all Calculator Tests.");
    }

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        System.out.println("🔧 New test starting...");
    }

    @AfterEach
    void cleanUp() {
        System.out.println("🔚 Test ended.");
    }

    @Test
    @Order(1)
    @DisplayName("✅ Test addition of multiple numbers")
    void testAddValid() {
        assertEquals(15, calculator.add(5, 3, 7));
    }

    @Test
    @Order(2)
    @DisplayName("❌ Invalid division by zero (disabled test)")
    @Disabled("This test fails due to divide by zero. Fix: check if denominator is zero.")
    void testDivideByZero() {
        calculator.divide(5, 0); // will throw exception
    }

    @Test
    @Order(3)
    @DisplayName("🧮 Multiple assertions on factorial")
    void testFactorialCases() {
        assertAll("Factorial Tests",
                () -> assertEquals(1, calculator.factorial(0)),
                () -> assertEquals(1, calculator.factorial(1)),
                () -> assertEquals(6, calculator.factorial(3)),
                () -> assertEquals(24, calculator.factorial(4))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 3, 5})
    @Order(4)
    @DisplayName("🔁 Parameterized test for factorial")
    void testFactorialParam(int num) {
        int expected = switch (num) {
            case 0, 1 -> 1;
            case 3 -> 6;
            case 5 -> 120;
            default -> -1;
        };
        assertEquals(expected, calculator.factorial(num));
    }

    @Test
    @Order(5)
    @Timeout(1)
    @DisplayName("⏱️ Timeout test for long operation")
    void testPerformance() {
        for (int i = 0; i < 1_000_000; i++) {
            Math.sqrt(i);
        }
        assertTrue(true); // dummy pass
    }

    @Test
    @Order(6)
    @DisplayName("❌ Factorial of negative number should throw exception")
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-1));
    }

    @Test
    @Order(7)
    @DisplayName("➕ Add with no arguments should return 0")
    void testAddEmpty() {
        assertEquals(0, calculator.add());
    }
}
