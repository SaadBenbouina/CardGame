package entity
import kotlin.test.*
/**
 * Test class for the CardSuit enum.
 */
class CardsuitTest {
    /**
     * Test the toString method for the CLUBS suit.
     */
    @Test

    fun testToStringClubs() {
        val suit = CardSuit.CLUBS
     assertEquals("♣", suit.toString())
    }
    /**
     * Test the toString method for the SPADES suit.
     */
    @Test

    fun testToStringSpades() {
        val suit = CardSuit.SPADES
     assertEquals("♠", suit.toString())
    }
    /**
     * Test the toString method for the HEARTS suit.
     */
    @Test

        fun testToStringHearts() {
        val suit = CardSuit.HEARTS
       assertEquals("♥", suit.toString())
    }
    /**
     * Test the toString method for the DIAMONDS suit.
     */
    @Test

    fun testToStringDiamonds() {
        val suit = CardSuit.DIAMONDS
        assertEquals("♦", suit.toString())
    }
}
