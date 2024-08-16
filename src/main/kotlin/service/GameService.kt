package service

import entity.*
import java.util.*
import kotlin.collections.ArrayDeque

/**
 * Die Klasse `GameService` stellt den Service-Layer für das Spiel dar.
 * Sie enthält Funktionen zum Starten und Beenden eines Spiels sowie zur Erstellung des Spieldecks und der Spielumgebung.
 *
 * @property rootService Der RootService, der den Zugriff auf das aktuelle Spiel ermöglicht.
 */
class GameService(private val rootService: RootService) : AbstractRefreshingService() {

    /**
     * Startet ein neues Spiel mit den angegebenen Spielern.
     *
     * @param playerList Die Liste der Spieler, die am Spiel teilnehmen.
     * @throws IllegalArgumentException, wenn die Spielerliste nicht genau 2 verschiedene Spieler enthält.
     */
    fun startNewGame(playerList: List<Player>) {
        // Karten mischen und Spielumgebung initialisieren
        val cards = createDeck().shuffled().toMutableList()
        val pyramid = initialisePyramid(cards.subList(24, 52))
        val drawPile = initialiseStack(cards.subList(0, 24))
        val table = Table(drawPile, pyramid)

        // Überprüfen, ob die Spielerliste genau 2 verschiedene Spieler enthält
        require(playerList[0] != playerList[1] && playerList.size == 2)
        { "Wählen Sie genau 2 Spieler mit 2 verschiedenen Namen aus." }

        // Spielzustand erstellen und im RootService speichern
        val game = GameState(table, playerList[0], playerList[1])
        rootService.currentGame = game
        onAllRefreshables { onGameStart() }
    }

    /**
     * Erstellt ein Kartendeck mit 52 Karten.
     *
     * @return Die erstellte Liste von Karten.
     */
    fun createDeck(): MutableList<Card> {
        val cards: ArrayDeque<Card> = ArrayDeque(52)
        for (suit: CardSuit in setOf(CardSuit.HEARTS, CardSuit.CLUBS, CardSuit.SPADES, CardSuit.DIAMONDS)) {
            for (value: CardValue in setOf(
                CardValue.TWO, CardValue.THREE, CardValue.FOUR, CardValue.FIVE, CardValue.SIX, CardValue.SEVEN,
                CardValue.EIGHT, CardValue.NINE, CardValue.TEN, CardValue.JACK, CardValue.QUEEN, CardValue.KING,
                CardValue.ACE
            )) {
                cards.add(Card(suit, value))
            }
        }
        return cards.toList().toMutableList()
    }

    /**
     * Beendet das aktuelle Spiel. Die Punktzahlen der Spieler werden ausgewertet.
     */
    fun endGame() {
        val game = rootService.currentGame
        checkNotNull(game) { "Noch kein Spiel gestartet." }
        onAllRefreshables { onGameFinished() }
    }

    /**
     * Initialisiert die Pyramide des Spiels.
     *
     * @param cards Die Liste der Karten, die für die Pyramide verwendet werden sollen.
     * @return Die initialisierte Pyramide als Liste von Listen von Karten.
     */
    fun initialisePyramid(cards: MutableList<Card>): MutableList<MutableList<Card?>> {
        val pyramid: MutableList<MutableList<Card?>> = mutableListOf()
        var index = 0

        for (floor in 1..7) {
            val floorCards: MutableList<Card?> = mutableListOf()
            val numberOfCards = 7 - floor + 1

            for (i in 0 until numberOfCards) {
                val card = cards[index]

                // Bedingungen für das Sichtbar-Machen von Karten in der Pyramide
                if (floor == 7) {
                    card.visible = true
                }
                if(i == 0){
                    card.visible = true
                }
                if(i == numberOfCards - 1){
                    card.visible = true
                }

                floorCards.add(card)
                index++
            }

            pyramid.add(floorCards)
        }

        return pyramid
    }

    /**
     * Initialisiert den Ziehstapel des Spiels.
     *
     * @param cards Die Liste der Karten, die für den Ziehstapel verwendet werden sollen.
     * @return Der initialisierte Ziehstapel als [Stack] von Karten.
     */
    fun initialiseStack(cards: MutableList<Card>): Stack<Card> {
        val drawStack: Stack<Card> = Stack()
        for (i in 0 until 24) {
            drawStack.add(cards[i])
        }
        return drawStack
    }
}