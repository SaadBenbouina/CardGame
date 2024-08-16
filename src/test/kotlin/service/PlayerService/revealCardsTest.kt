package service.PlayerService
import entity.*
import org.junit.jupiter.api.assertThrows

import kotlin.test.*
import service.*
import service.RootService
/**
 * actionRemovePairTest class provides tests for the revealCards class's actionRemovePair functionality.
 */
class revealCardsTest {
    private val rootService = RootService()
    private val gameService = GameService(rootService)
    private val playerActionService = PlayerService(rootService)
    private val player1 = Player("bob")
    private val player2= Player("lol")
    /**
     * Tests ob die erste und letzte kart sichtbar sind
     */
    @Test
    fun test(){
        assertThrows< IllegalStateException > {
            PlayerService(rootService).revealCards()
        }
        gameService.startNewGame(listOf(player1,player2))
        val game = rootService.currentGame
        requireNotNull(game)
        game.table.pyramid[0][0]!!.visible =false
        game.table.pyramid[0][6]!!.visible =false
        playerActionService.revealCards()

        assertTrue {game.table.pyramid[0].first()!!.visible }
        assertTrue {game.table.pyramid[0].last()!!.visible }
        assertTrue {game.table.pyramid[1].first()!!.visible }
        assertTrue {game.table.pyramid[1].last()!!.visible }
        assertTrue {game.table.pyramid[2].first()!!.visible }
        assertTrue {game.table.pyramid[2].last()!!.visible }
        assertTrue {game.table.pyramid[3].first()!!.visible }
        assertTrue {game.table.pyramid[3].last()!!.visible }
        assertTrue {game.table.pyramid[4].first()!!.visible }
        assertTrue {game.table.pyramid[4].last()!!.visible }
        assertTrue {game.table.pyramid[5].first()!!.visible }
        assertTrue {game.table.pyramid[5].last()!!.visible }
        assertTrue {game.table.pyramid[6].first()!!.visible }
        assertTrue {game.table.pyramid[6].last()!!.visible }
    }
}