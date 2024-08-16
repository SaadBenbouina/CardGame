package view
import service.*
import entity.*

/**
 * This interface provides a mechanism for the service layer classes to communicate
 * (usually to the view classes) that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 *
 * Default (empty) implementations are provided for all methods, so that implementing
 * UI classes only need to react to events relevant to them.
 *
 * @see AbstractRefreshingService
 *
 */
interface Refreshable {


    /**
     * perform refreshes that are necessary after a new game started
     */
    fun onGameStart() {}


    /**
     * perform refreshes after a player has drawn from a card from the drawStack
     */
    fun onActionDrawCard() {}

    /**
     * perform refreshes after a player has drawn  from the Pyramid (or the drawStack)
     *
     * @param card1 first card of the pair
     * @param card2 second card of the pair
     */
    fun onActionRemovePair(card1: Card, card2: Card) {}

    /**
     * perform refreshes after a player has Passed
     */
    fun onActionSitOut() {

    }

    /**
     * um ein update von score zu geben
     */
    fun updateScore(){

    }

    /**
     * perform refreshes after a player did not remove  a new Pair(or card)
     */
    fun reloadscene(){}
    /**
     * perform refreshes after the game has ended
     *
     */
    fun onGameFinished() {}


}
