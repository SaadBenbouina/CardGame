package service.PlayerService
import kotlin.test.*
import service.*
import service.RootService
import entity.*
import org.junit.jupiter.api.assertThrows

/**
 * swapPlayerTest class provides tests for the [PlayerService] class's swapplayer functionality.
 */
class swapPlayerTest {

        /**
         * Tests the swap functionality of [PlayerService].
         */
@Test

    fun testswap(){
     val rootService = RootService()
     val playerActionService = PlayerService(rootService)
     val gameService = GameService(rootService)
     val player1 = Player("bob")
     val player2= Player("lol")
            assertThrows< IllegalStateException > {
                playerActionService.swapPlayer()

            }
    gameService.startNewGame(listOf(player1,player2))
    val game = rootService.currentGame
    requireNotNull(game)
    if(game.currentPlayer==player1){
        playerActionService.swapPlayer()
        assertEquals(player2,game.currentPlayer)
    }
    else{
        playerActionService.swapPlayer()
        assertEquals(player1,game.currentPlayer)
    }


    }
}
