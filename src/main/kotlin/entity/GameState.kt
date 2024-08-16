package entity

import kotlin.random.Random

/**
 * Entity class that represents a game state of "Active". Besides knowing the players [Player] and
* the current player [currentPlayer] and the information on how many players have passed in

*/
data class GameState(val table: Table,val player1:Player ,val player2:Player ){
    var sitOutCount :Int =0
    var currentPlayer: Player = if (Random.nextBoolean()) player1 else player2
}
