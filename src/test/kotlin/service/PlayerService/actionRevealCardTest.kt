package service.PlayerService

import entity.*
import org.junit.jupiter.api.assertThrows

import kotlin.test.*
import service.*
import service.RootService

/**
 * actionRevealCardTest class provides tests for the [PlayerService] class's actionRevealCard functionality.
 *
 * @property rootService A [RootService] instance used to initialize other services.
 * @property gameService A [GameService] instance used to manage game-related functionalities.
 * @property playerActionService A [PlayerService] instance being tested.
 * @property player1 A sample [Player] instance with the name "bob" for testing.
 * @property player2 A sample [Player] instance with the name "lol" for testing.
 */
class actionRevealCardTest {
    private val rootService = RootService()
    private val gameService = GameService(rootService)
    private val playerActionService = PlayerService(rootService)
    private val player1 = Player("bob")
    private val player2= Player("lol")
    /**
     * Tests the actionRevealCard functionality of [PlayerService].
     */
    @Test
    fun testactionRevealCardTest(){
        // Ensure that attempting to reveal a card without an active game results in an exception

        //if we use reservestack
        assertThrows< IllegalStateException > {
            PlayerService(rootService).actionRevealCard()
        }
        gameService.startNewGame(listOf(player1,player2))
        val game = rootService.currentGame
        requireNotNull(game)
        assertEquals(game.table.reserveStack.size,0)
        assertEquals (game.table.drawPile.size,24)
        if(game.currentPlayer==player1) {
            playerActionService.actionRevealCard()
            assertEquals(game.currentPlayer,player2)}
        else{
            playerActionService.actionRevealCard()
            assertEquals(game.currentPlayer,player1)}
            assertEquals(game.table.reserveStack.size, 1)
            assertEquals(game.table.drawPile.size, 23)
            val carInTopOfReserve = game.table.reserveStack.peek()
            assertTrue { carInTopOfReserve.visible }
        game.table.drawPile.clear()
        assertThrows< IllegalStateException > {
            PlayerService(rootService).actionRevealCard()
        }



    }
}