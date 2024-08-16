package service

import entity.*

/**
 * Diese Klasse stellt den Service-Layer für das Spiel "Pyramid" dar.
 * Sie enthält die Logik für die drei möglichen Aktionen eines Spielers:
 * Entfernen eines Paares, Aufdecken einer Karte und Passen.
 *
 * @property rootService Der RootService, der den Zugriff auf das aktuelle Spiel ermöglicht.
 */
class PlayerService(private val rootService: RootService) : AbstractRefreshingService() {

    /**
     * Entfernt ein Paar von sichtbaren Karten aus dem Spiel.
     */
    fun actionRemovePair(cardA: Card, cardB: Card) {

        // Überprüfen, ob beide Karten sichtbar sind
     if(cardA.visible == true && cardB.visible == true){
        // Aktuelles Spiel abrufen
        val game = rootService.currentGame
        checkNotNull(game)

        // Punkte für das Ziehen des Paares berechnen
        val score = scoreForDrawing(cardA, cardB)

        // Falls keine Punkte vergeben wurden, Spiel neu laden und Fehler werfen
        if (score == 0) {
            onAllRefreshables { reloadscene() }
            throw Exception("Ungültiges Paar ausgewählt")
        }
        else{
            if (cardA in game.table.reserveStack) {
                game.table.reserveStack.remove(cardA)
            } else {
                game.table.pyramid.forEach { floorCards ->
                    floorCards.remove(cardA)
                }
            }

            if (cardB in game.table.reserveStack) {
                game.table.reserveStack.remove(cardB)
            } else {
                game.table.pyramid.forEach { floorCards ->
                    floorCards.remove(cardB)
                }
            }
            var counter =0
            for(i in 0..6){
                if(game.table.pyramid[i].isEmpty()){
                    counter+=1
                }
            }
            if(counter==7){
                rootService.gameService.endGame()

                rootService.currentGame = null
            }
           else{ game.sitOutCount = 0
            game.currentPlayer.score += score
            revealCards()
            swapPlayer()
            onAllRefreshables { onActionRemovePair(cardA, cardB) }
            onAllRefreshables { updateScore() }
           }
        }
     }
   }
    /**
     * Macht die Nachbarn einer gegebenen Karte sichtbar.
     * @throws IllegalStateException, wenn das Spiel nicht gestartet wurde.
     */
    fun revealCards() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet." }

        val cards: MutableList<Card> = mutableListOf()

        for (floorCards in game.table.pyramid) {
            if (floorCards.isNotEmpty()) {
                val firstCard = floorCards.first()
                val lastCard = floorCards.last()

                if (!firstCard!!.visible) {
                    firstCard.visible = true
                    cards.add(firstCard)
                }
                if (!lastCard!!.visible) {
                    lastCard.visible = true
                    cards.add(lastCard)
                }
            }
        }

    }


    /**
     * Gibt den Namen des aktuellen Spielers zurück.
     * @return Der Name des aktuellen Spielers.
     * @throws IllegalStateException, wenn das Spiel nicht gestartet wurde.
     */
    fun currentPlayerName(): String {
        val game = rootService.currentGame
        checkNotNull(game) { "Das Spiel muss gestartet werden" }
        return game.currentPlayer.name
    }

    /**
     * Get the score for a card pair.
     * @returns 1 if theplayer use jocker
     * or 2 for the score of 15
     */
     fun scoreForDrawing(card1:Card ,card2:Card):Int{
        val game = rootService.currentGame
        checkNotNull(game)
        if(card1.value.intValue()+card2.value.intValue()==15) {
            return 2
        }
        if((card1.value.intValue()==1) xor (card2.value.intValue()==1)){
            return 1
        }
        return 0
    }

    /**
     * Methode um kart von draw zu reservestack bringen
     */
    fun actionRevealCard() {

     // Get the game state object from the root service

        val game = rootService.currentGame
        checkNotNull(game)
        val drawPile = game.table.drawPile
        check( drawPile.isNotEmpty()) { "Cannot draw from empty pile" }

        val  cardtoput =drawPile.pop()
        cardtoput.visible=true
        game.table.reserveStack.push(cardtoput)
        game.sitOutCount=0
        swapPlayer()
        onAllRefreshables{onActionDrawCard()}
        if(game.table.pyramid.isEmpty()){
            onAllRefreshables { onGameFinished() }
        }
    }



    /**
     *
     *um den Action pass auszuführen
     * */
    fun pass() {

        val game = rootService.currentGame
        checkNotNull(game)
        game.sitOutCount++
        swapPlayer()
        if (game.sitOutCount == 2) {
            rootService.gameService.endGame()
            rootService.currentGame = null
        }
        else{
        onAllRefreshables { onActionSitOut()}
        }
    }

     fun swapPlayer(){
        val game = rootService.currentGame
        checkNotNull(game)
        if (game.currentPlayer == game.player1) {
            game.currentPlayer = game.player2
        } else {
            game.currentPlayer = game.player1
        }
    }
}