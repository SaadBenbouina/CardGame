package entity

import kotlin.test.*
/**
 * Testet die [Player]-Klasse.
 */
class Playertest {
    /**
     * Überprüft, ob der Spielername korrekt gesetzt und abgerufen wird.
     */

    @Test
    fun testPlayerName() {
    val playerName = "John"
    val player = Player(playerName)

        assertEquals(playerName, player.name)

    }
    /**
     * Überprüft, ob der Spieler mit dem richtigen Initialpunktestand erstellt wird.
     */
    @Test

    fun testInitialScore() {
        val player = Player("Alice")

       assertEquals(0, player.score)
    }
    /**
     * Überprüft, ob der Punktestand des Spielers erfolgreich aktualisiert wird.
     */
    @Test

    fun testUpdateScore() {
        val player = Player("Bob")
        player.score = 100
        assertEquals(100, player.score)
    }
}
