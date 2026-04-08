package main.najah.test;
import main.najah.code.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        System.out.println("🔐 Starting UserService test...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("✅ Finished UserService test.");
    }

    @Test
    @Order(1)
    @DisplayName("✅ Valid email should return true")
    void testValidEmail() {
        assertTrue(userService.isValidEmail("test@example.com"));
    }

    @Test
    @Order(2)
    @DisplayName("❌ Invalid email should return false")
    void testInvalidEmail() {
        assertFalse(userService.isValidEmail("notanemail"));
    }

    @Test
    @Order(3)
    @DisplayName("✅ Correct credentials should authenticate")
    void testAuthenticateValid() {
        assertTrue(userService.authenticate("admin", "1234"));
    }

    @Test
    @Order(4)
    @DisplayName("❌ Incorrect credentials should not authenticate")
    void testAuthenticateInvalid() {
        assertFalse(userService.authenticate("admin", "wrong"));
        assertFalse(userService.authenticate("user", "1234"));
    }

    @ParameterizedTest
    @CsvSource({
        "admin, 1234, true",
        "admin, wrong, false",
        "user, 1234, false",
        "test, test, false"
    })
    @Order(5)
    @DisplayName("🔁 Parameterized authentication test")
    void testMultipleAuthCases(String username, String password, boolean expected) {
        boolean result = userService.authenticate(username, password);
        assertEquals(expected, result);
    }

    @Test
    @Order(6)
    @DisplayName("❌ Invalid email format should return false")
    void testBrokenEmail() {
        assertFalse(userService.isValidEmail("invalid_email"));
    }

    @Test
    @Order(7)
    @Timeout(1)
    @DisplayName("⏱️ Timeout test for repeated authentication")
    void testAuthTimeout() {
        for (int i = 0; i < 1_000_000; i++) {
            userService.authenticate("admin", "1234");
        }
        assertTrue(true);
    }

    @Test
    @Order(8)
    @DisplayName("📧 Null email should return false")
    void testNullEmail() {
        assertFalse(userService.isValidEmail(null));
    }

    @Test
    @Order(9)
    @DisplayName("📧 Email without dot or at-sign should be invalid")
    void testBadEmailFormat() {
        assertFalse(userService.isValidEmail("email.com"));
        assertFalse(userService.isValidEmail("email@com"));
    }

    @Test
    @Order(10)
    @DisplayName("📧 Email with @ but no dot should return false")
    void testEmailAtNoDot() {
        assertFalse(userService.isValidEmail("user@domain"));
    }

    @Test
    @Order(11)
    @DisplayName("📧 Email with dot but no @ should return false")
    void testEmailDotNoAt() {
        assertFalse(userService.isValidEmail("user.domain.com"));
    }
}
