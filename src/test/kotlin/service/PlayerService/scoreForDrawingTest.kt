package service.PlayerService
import entity.*
import org.junit.jupiter.api.assertThrows
import kotlin.test.*
import service.*
import service.RootService
/**
 * Test if pass works correctly if the score is good calculeted.
 */
class scoreForDrawingTest {
    private val rootService = RootService()
    private val gameService = GameService(rootService)
    private val playerActionService = PlayerService(rootService)
    private val suit1 = CardSuit.CLUBS
    private val value1 = CardValue.EIGHT
    private val card1 = Card(suit1, value1)
    private val suit2 = CardSuit.SPADES
    private val value2 = CardValue.ACE
    private val card2 = Card(suit2, value2)
    private val suit3 = CardSuit.DIAMONDS
    private val value3 = CardValue.FOUR
    private val card3 = Card(suit3, value3)
    private val suit4 = CardSuit.DIAMONDS
    private val value4 = CardValue.SEVEN
    private val card4 = Card(suit4, value4)
    private val player1 = Player("bob")
    private val player2= Player("lol")
    /**
     * Überprüft ob die score von einige karten  gut gerechnet
     */
    @Test
    fun test(){
        val card5=Card(CardSuit.DIAMONDS,CardValue.ACE)
        assertThrows< IllegalStateException > {
            PlayerService(rootService).scoreForDrawing(card1,card2)
        }
        gameService.startNewGame(listOf(player1,player2))
        assertEquals(playerActionService.scoreForDrawing(card1,card2),1)
        assertEquals(playerActionService.scoreForDrawing(card1,card3),0)
        assertEquals(playerActionService.scoreForDrawing(card1,card4),2)
        assertEquals(playerActionService.scoreForDrawing(card2,card5),0)



    }
}