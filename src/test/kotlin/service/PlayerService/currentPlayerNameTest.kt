package service.PlayerService
import entity.*
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.*
import service.*
import service.RootService
/**
 * currentPlayerNameTest class provides tests for the [PlayerService] class's currentPlayerName functionality.
 *
 * @property rootService A [RootService] instance used to initialize other services.
 * @property gameService A [GameService] instance used to manage game-related functionalities.
 * @property player1 A sample [Player] instance with the name "bob" for testing.
 * @property player2 A sample [Player] instance with the name "lol" for testing.
 */
class currentPlayerNameTest {
    private val rootService = RootService()
    private val gameservice = GameService(rootService)
    private val player1 = Player("bob")
    private val player2= Player("lol")
    /**
     * Tests the currentPlayerName functionality of [PlayerService].
     */
    @Test
    fun testcurrentPlayerName() {
        // Ensure that there is no currently active game initially

        assertNull(rootService.currentGame)
        // Start a new game with a list of players
        assertThrows< IllegalStateException > {
            PlayerService(rootService).currentPlayerName()
        }
        gameservice.startNewGame(listOf(player1, player2))

        assertNotNull(rootService.currentGame) // Check if game was created.
        val  game =rootService.currentGame
        checkNotNull(game)
        val currentplayerName = game.currentPlayer.name
        val playername =PlayerService(rootService).currentPlayerName()
        // Check if the names match

        assertEquals(currentplayerName,playername)

    }
}
