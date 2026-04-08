package main.najah.test;
import main.najah.code.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeBookTest {

    RecipeBook recipeBook;
    Recipe recipe;

    @BeforeEach
    void setup() throws RecipeException {
        recipeBook = new RecipeBook();
        recipe = new Recipe();
        recipe.setName("Latte");
        recipe.setPrice("10");
        recipe.setAmtCoffee("2");
        recipe.setAmtMilk("1");
        System.out.println("📚 Starting RecipeBook test...");
    }

    @AfterEach
    void teardown() {
        System.out.println("✅ Finished RecipeBook test.");
    }

    @Test
    @Order(1)
    @DisplayName("✅ Add valid recipe")
    void testAddRecipe() {
        assertTrue(recipeBook.addRecipe(recipe));
    }

    @Test
    @Order(2)
    @DisplayName("❌ Add duplicate recipe")
    void testAddDuplicateRecipe() {
        recipeBook.addRecipe(recipe);
        assertFalse(recipeBook.addRecipe(recipe));
    }

    @Test
    @Order(3)
    @DisplayName("🗑️ Delete existing recipe")
    void testDeleteRecipe() {
        recipeBook.addRecipe(recipe);
        assertEquals("Latte", recipeBook.deleteRecipe(0));
    }

    @Test
    @Order(4)
    @DisplayName("❌ Delete non-existent recipe")
    void testDeleteInvalidRecipe() {
        assertNull(recipeBook.deleteRecipe(0));
    }

    @Test
    @Order(5)
    @DisplayName("✏️ Edit existing recipe")
    void testEditRecipe() throws RecipeException {
        recipeBook.addRecipe(recipe);
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Espresso");
        newRecipe.setPrice("12");
        newRecipe.setAmtCoffee("1");
        newRecipe.setAmtMilk("2");
        assertEquals("Latte", recipeBook.editRecipe(0, newRecipe));
    }

    @Test
    @Order(6)
    @DisplayName("⚠️ Edit null recipe should return null")
    void testEditNullRecipe() throws RecipeException {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Mocha");
        newRecipe.setPrice("11");
        newRecipe.setAmtCoffee("3");
        newRecipe.setAmtMilk("1");
        assertNull(recipeBook.editRecipe(0, newRecipe));
    }

    @Test
    @Order(7)
    @DisplayName("☕ Recipe equals - same name")
    void testRecipeEqualsTrue() {
        Recipe r1 = new Recipe();
        r1.setName("Latte");
        Recipe r2 = new Recipe();
        r2.setName("Latte");
        assertTrue(r1.equals(r2));
    }

    @Test
    @Order(8)
    @DisplayName("☕ Recipe equals - different name")
    void testRecipeEqualsFalse() {
        Recipe r1 = new Recipe();
        r1.setName("Latte");
        Recipe r2 = new Recipe();
        r2.setName("Mocha");
        assertFalse(r1.equals(r2));
    }

    @Test
    @Order(9)
    @DisplayName("☕ setAmtCoffee valid")
    void testSetAmtCoffeeValid() throws RecipeException {
        recipe.setAmtCoffee("3");
        assertEquals(3, recipe.getAmtCoffee());
    }

    @Test
    @Order(10)
    @DisplayName("❌ setAmtCoffee invalid string")
    void testSetAmtCoffeeInvalidString() {
        assertThrows(RecipeException.class, () -> recipe.setAmtCoffee("abc"));
    }

    @Test
    @Order(11)
    @DisplayName("❌ setAmtCoffee negative number")
    void testSetAmtCoffeeNegative() {
        assertThrows(RecipeException.class, () -> recipe.setAmtCoffee("-1"));
    }

    @Test
    @Order(12)
    @DisplayName("🥛 setAmtMilk valid")
    void testSetAmtMilkValid() throws RecipeException {
        recipe.setAmtMilk("2");
        assertEquals(2, recipe.getAmtMilk());
    }

    @Test
    @Order(13)
    @DisplayName("❌ setAmtMilk invalid string")
    void testSetAmtMilkInvalidString() {
        assertThrows(RecipeException.class, () -> recipe.setAmtMilk("xyz"));
    }

    @Test
    @Order(14)
    @DisplayName("❌ setAmtMilk negative")
    void testSetAmtMilkNegative() {
        assertThrows(RecipeException.class, () -> recipe.setAmtMilk("-5"));
    }

    @Test
    @Order(15)
    @DisplayName("💰 setPrice valid")
    void testSetPriceValid() throws RecipeException {
        recipe.setPrice("5");
        assertEquals(5, recipe.getPrice());
    }

    @Test
    @Order(16)
    @DisplayName("❌ setPrice invalid string")
    void testSetPriceInvalidString() {
        assertThrows(RecipeException.class, () -> recipe.setPrice("abc"));
    }

    @Test
    @Order(17)
    @DisplayName("❌ setPrice negative")
    void testSetPriceNegative() {
        assertThrows(RecipeException.class, () -> recipe.setPrice("-1"));
    }

    @Test
    @Order(18)
    @DisplayName("🔁 toString returns name")
    void testToString() {
        recipe.setName("Latte");
        assertEquals("Latte", recipe.toString());
    }

    @Test
    @Order(19)
    @DisplayName("🔑 hashCode is consistent with name")
    void testHashCode() {
        recipe.setName("Latte");
        int hash1 = recipe.hashCode();
        int hash2 = recipe.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    @Order(20)
    @DisplayName("🧪 hashCode with null name")
    void testHashCodeWithNullName() {
        Recipe r = new Recipe();
        r.setName(null);
        int hash = r.hashCode();
        assertEquals(hash, r.hashCode());
    }
}
