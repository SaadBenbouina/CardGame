package service.GameService
import entity.*
import kotlin.test.*
import service.*
import org.junit.jupiter.api.assertThrows

/**
 * Class that provides tests for GameService.
 *
 *
 * @property rootService A [RootService] instance used to initialize the GameService.
 * @property gameservice The [GameService] instance to be tested.
 * @property player1 A sample [Player] instance with the name "bob" for testing.
  * @property player2 A sample [Player] instance with the name "lol" for testing.
 */

class startNewGameTest {
    private val rootService = RootService()
    private val gameservice = GameService(rootService)

    private val player1 = Player("bob")
    private val player2 = Player("lol")
    private val player3 = Player("bob")


    /**
     * Tests the startNewGame function of [GameService].
     */
    @Test
    fun teststartNewGame() {
        assertNull(rootService.currentGame)
        // Start a new game with a list of players
        assertThrows<IllegalStateException > {
            gameservice.endGame()
        }
        assertThrows<IllegalArgumentException> {
            gameservice.startNewGame(listOf(player1, player1))

        }

        gameservice.startNewGame(listOf(player1, player2))

        assertNotNull(rootService.currentGame) // Check if game was created.

        val currentGame = rootService.currentGame
        requireNotNull(currentGame)
        assertEquals(7,currentGame.table.pyramid.size)
        assertEquals(24,currentGame.table.drawPile.size)
        // Check if properties of game are correct.
        assertNotEquals(player1.name, player2.name)
        gameservice.endGame()
        assertNotNull(rootService.currentGame)
        gameservice.startNewGame(listOf(player1, player3))
         assertEquals(player1.name, player3.name)


    }
}
