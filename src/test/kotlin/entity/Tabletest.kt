package entity
import java.util.*
import kotlin.test.*

/**
 * TableTest class is responsible for testing the functionalities of the Table class.
 *
 */
class TableTest {
    /**
     * Überprüft ob die size von reservestack ist gleich zu 0
     */
    @Test

    fun testTable() {
        // Erstellen einer Instanz von GameState für den Test

        val drawpile: Stack<Card> = Stack()
        val pyramid: MutableList<MutableList<Card?>> = mutableListOf()
        val table = Table(drawpile, pyramid)

        // Überprüfen, ob der aktuelle Spieler, die Tabelle und die Spieler korrekt initialisiert wurden
        assertEquals(0, table.reserveStack.size)


    }
}









