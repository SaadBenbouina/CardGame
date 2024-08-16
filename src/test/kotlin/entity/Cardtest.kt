package entity
import kotlin.test.*
/**
 * Unit tests for the Card class.
 */
class Cardtest {
    /**
     * Test case for creating a Card instance.
     */
    @Test

    fun testCardCreation() {
        val suit = CardSuit.CLUBS
        val value = CardValue.EIGHT
       val card = Card(suit, value)

       assertEquals(suit, card.suit)
        assertEquals(value, card.value)
        assertFalse(card.visible)
    }
    /**
     * Test case for checking card visibility.
     */
    @Test

    fun testCardVisibility() {
        val suit = CardSuit.SPADES
        val value = CardValue.ACE
        val card = Card(suit, value)

        // Initially, the card should be visible
        assertFalse(card.visible)

        // Change the visibility and check again
        card.visible = true
       assertTrue(card.visible)
    }
}
