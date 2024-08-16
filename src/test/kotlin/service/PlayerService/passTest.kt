package service.PlayerService
import entity.*
import org.junit.jupiter.api.assertThrows
import kotlin.test.*
import service.*
import service.RootService
/**
 * Test if pass works correctly if the middle stack is not to be exchanged.
 */
class passTest {
    private val rootService = RootService()
    private val gameService = GameService(rootService)
    private val playerActionService = PlayerService(rootService)
    private val player1 = Player("bob")
    private val player2= Player("lol")

    /**
     * Überprüft ob die Eigenschaft von pass gut laufen
     */
    @Test
    fun testpass(){
        assertThrows< IllegalStateException > {
            PlayerService(rootService).pass()
        }

        gameService.startNewGame(listOf(player1,player2))

        val currentGame = rootService.currentGame
        requireNotNull(currentGame)


        playerActionService.pass()

        assertEquals(currentGame.sitOutCount,1) // Check if numberPassed was incremented.

        playerActionService.pass()
         assertEquals(null, rootService.currentGame)

    }
}