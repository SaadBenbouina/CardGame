package service.PlayerService
import entity.*
import org.junit.jupiter.api.assertThrows
import kotlin.test.*
import service.*
import service.RootService
/**
 * actionRemovePairTest class provides tests for the [PlayerService] class's actionRemovePair functionality.
 *
 * @property rootService A [RootService] instance used to initialize other services.
 * @property gameService A [GameService] instance used to manage game-related functionalities.
 * @property playerActionService A [PlayerService] instance being tested.
 * @property suit1 A sample [CardSuit] instance for testing.
 * @property value1 A sample [CardValue] instance for testing.
 * @property card1 A sample [Card] instance created with suit1 and value1.
 * @property suit2 A sample [CardSuit] instance for testing.
 * @property value2 A sample [CardValue] instance for testing.
 * @property card2 A sample [Card] instance created with suit2 and value2.
 * @property player1 A sample [Player] instance with the name "bob" for testing.
 * @property player2 A sample [Player] instance with the name "lol" for testing.
 */
class actionRemovePairTest {
    private val rootService = RootService()
    private val gameService = GameService(rootService)
    private val playerActionService = PlayerService(rootService)
    private val suit1 = CardSuit.CLUBS
    private val value1 = CardValue.EIGHT
    private val card1 = Card(suit1, value1)
    private val suit8 = CardSuit.DIAMONDS
    private val value8 = CardValue.EIGHT
    private val card8 = Card(suit8, value8)
    private val suit2 = CardSuit.SPADES
    private val value2 = CardValue.ACE
    private val card2 = Card(suit2, value2)
    private val player1 = Player("bob")
    private val player2= Player("lol")

    /**
     * Tests the actionRemovePair functionality of [PlayerService].
     */
    @Test
    fun testactionRemovePair(){
        // Create a deck for testing
        // Create a deck for testing

        card1.visible=true
        card2.visible=true
        card8.visible=true
        //if we use reservestack
        gameService.startNewGame(listOf(player1,player2))
        val game = rootService.currentGame
        requireNotNull(game)
        assertThrows<Exception> {
            playerActionService.actionRemovePair(card1, card8)

        }
        //if we do  use reservestack
        for(i in 0..6){
            if( game.table.pyramid[i].contains(card2)) {
                game.table.reserveStack.push(card1)
                playerActionService.actionRemovePair(card1, card2)

                assertEquals(game.table.reserveStack.size, 0)
            }
        }

        val card5 = Card(CardSuit.SPADES, CardValue.SIX)
        val card6 = Card(CardSuit.DIAMONDS, CardValue.NINE)
        card5.visible = true
        card6.visible = true
        game.table.reserveStack.push(card5)
        val size =game.table.reserveStack.size
        playerActionService.actionRemovePair(card6,card5)
        assertEquals(size-1,game.table.reserveStack.size)
        game.table.pyramid.forEach { floorCards ->
            assertTrue {!floorCards.contains(card6) }
        }

        val card7 = Card(CardSuit.CLUBS, CardValue.SEVEN)
        val card8 = Card(CardSuit.HEARTS, CardValue.EIGHT)
        card7.visible = true
        card8.visible = true
        var counter3 =0
        for(i in 0..6){
            if (game.table.pyramid[i].contains(card7)){ counter3 +=1}
            if (game.table.pyramid[i].contains(card8)){ counter3 +=1}
        }

        if(counter3==2){
            playerActionService.actionRemovePair(card7,card8)
            for(i in 0..6){
                assertTrue(!game.table.pyramid[i].contains(card7))
                assertTrue(!game.table.pyramid[i].contains(card8))}
        }
        var counter4=0
        for(i in 0..6){
            game.table.pyramid[i].clear()
            counter4+=1
        }
        if(counter4==7) {
            playerActionService.actionRemovePair(card1,card2)
            assertEquals(null, rootService.currentGame)
        }



    }
}