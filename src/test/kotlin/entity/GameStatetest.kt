package entity
import java.util.*
import kotlin.random.Random
import kotlin.test.*
/**
 * Unit test for the initial state of the GameState class.
 */

class GameStatetest {

    /**
     * Tests the initial state of GameState.
     */
    @Test
    fun testInitialState() {

        // Erstellen einer Instanz von GameState für den Test
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val drawpile: Stack<Card> = Stack()
        val pyramid : MutableList<MutableList<Card?>> = mutableListOf()
        val table = Table (drawpile,pyramid)
        val initialState = GameState(table,player1 , player2)

        // Überprüfen, ob der aktuelle Spieler, die Tabelle und die Spieler korrekt initialisiert wurden
        assertEquals(table, initialState.table)
        assertEquals(player1, initialState.player1)
        assertEquals(player2, initialState.player2)

        // Überprüfen, ob sitOutCount zu Beginn 0 ist
        assertEquals(0, initialState.sitOutCount)
    }
}
