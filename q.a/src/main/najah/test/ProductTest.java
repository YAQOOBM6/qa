package main.najah.test;
import main.najah.code.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTest {

    Product product;

    @BeforeEach
    void setup() {
        product = new Product("Milk", 2.5, 10);
        System.out.println("🔧 Starting Product test...");
    }

    @AfterEach
    void teardown() {
        System.out.println("✅ Finished Product test.");
    }

    @Test
    @Order(1)
    @DisplayName("✅ Valid product data")
    void testValidProduct() {
        assertAll("Product Info",
                () -> assertEquals("Milk", product.getName()),
                () -> assertEquals(2.5, product.getPrice()),
                () -> assertEquals(10, product.getQuantity())
        );
    }

    @Test
    @Order(2)
    @DisplayName("❌ Invalid negative price - should throw exception")
    void testNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Bad", -5.0, 3));
    }

    @Test
    @Order(3)
    @DisplayName("🧪 Change quantity")
    void testSetQuantity() {
        product.setQuantity(20);
        assertEquals(20, product.getQuantity());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Bread", "Cheese", "Juice"})
    @Order(4)
    @DisplayName("🔁 Parameterized test for product names")
    void testProductNames(String name) {
        Product p = new Product(name, 1.0, 5);
        assertEquals(name, p.getName());
    }

    @Test
    @Order(5)
    @Timeout(1)
    @DisplayName("⏱️ Timeout test for price access")
    void testPricePerformance() {
        for (int i = 0; i < 1_000_000; i++) {
            product.getPrice();
        }
        assertTrue(true); // dummy
    }

    @Test
    @Order(6)
    @DisplayName("✅ Apply valid discount")
    void testApplyValidDiscount() {
        product.applyDiscount(10);
        assertEquals(10, product.getDiscount());
    }

    @Test
    @Order(7)
    @DisplayName("❌ Apply invalid discount should throw exception")
    void testApplyInvalidDiscount() {
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(-10));
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(100));
    }

    @Test
    @Order(8)
    @DisplayName("💰 Calculate final price after discount")
    void testFinalPrice() {
        product.applyDiscount(20); // 20% off
        assertEquals(2.0, product.getFinalPrice(), 0.01);
    }

    @Test
    @Order(9)
    @DisplayName("💰 Final price without discount should equal price")
    void testFinalPriceNoDiscount() {
        assertEquals(product.getPrice(), product.getFinalPrice(), 0.01);
    }

    @Test
    @Order(10)
    @DisplayName("📦 Constructor without quantity should default to 0")
    void testConstructorWithoutQuantity() {
        Product p = new Product("Water", 1.0);
        assertEquals(0, p.getQuantity());
    }

    @Test
    @Order(999)
    @DisplayName("❌ Division by zero should throw ArithmeticException")
    void testDivideByZero() {
        Calculator calculator = new Calculator();
        assertThrows(ArithmeticException.class, () -> calculator.divide(5, 0));
    }
}
