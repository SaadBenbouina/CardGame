package entity
import java.util.*
/**
 * Entity to represent a table in the game "Pyramide". Besides having a [drawPile] and [pyramid] the reserveStack
 */
data class Table(val drawPile: Stack<Card>, val pyramid: MutableList<MutableList<Card?>>) {
    var reserveStack: Stack<Card> = Stack()
}
