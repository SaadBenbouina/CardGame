package view
import entity.*
import service.*
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.BidirectionalMap
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.util.*
import kotlin.system.exitProcess


/**
 * This is the main thing for the  game. The scene shows the complete table at once.
 * Player 1 "sits" is on the bottom half of the screen, player 2 on the top.
 */
class GameScene(private val rootService: RootService) : BoardGameScene(1920, 1080), Refreshable {


    private var cardtoRemove: MutableList<Card> = mutableListOf()

    private val currentPlayerLabel =
        Label(posX = 800, posY = 900, width = 400.0, height = 100.0, font = Font(30), alignment = Alignment.CENTER)
    private val scorePlayer1Label = Label(
        posX = 1600,
        posY = 200,
        width = 300.0,
        height = 100.0,
        font = Font(30),
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )
    private val scorePlayer2Label = Label(
        posX = 0,
        posY = 200,
        width = 300.0,
        height = 100.0,
        font = Font(30),
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )
    private val drawStack = LabeledStackView(posX = 400, posY = 830)
    private val drawstackLabel =
        Label(posX = 300, posY = 1000, width = 300.0, height = 50.0, font = Font(25), alignment = Alignment.CENTER)
    private val reserveStack = LabeledStackView(posX = 1640, posY = 830).apply {
        onMouseClicked = {
            if(cardtoRemove.isEmpty()){
                cardtoRemove.add(0, rootService.currentGame!!.table.reserveStack.peek())

            }else{
                if(cardtoRemove.size<=2){
                    cardtoRemove.add(1, rootService.currentGame!!.table.reserveStack.peek())
                    rootService.playerService.actionRemovePair(cardtoRemove[0],cardtoRemove[1])
                    cardtoRemove.clear()}

            }
        }
    }


    private val reservestackLabel =
        Label(posX = 1550, posY = 1000, width = 300.0, height = 50.0, font = Font(25), alignment = Alignment.CENTER)
    private val linearLayout1 =  LinearLayout<CardView>(posX =700, posY =760,width = 830)
    private val linearLayout2 =  LinearLayout<CardView>(730, 640, 740, 0)
    private val linearLayout3 =  LinearLayout<CardView>(760, 520, 650, 0)
    private val linearLayout4 =  LinearLayout<CardView>(790, 400, 600, 0)
    private val linearLayout5 =  LinearLayout<CardView>(820, 280, 490, 0)
    private val linearLayout6 =  LinearLayout<CardView>(850, 160, 415, 0)
    private val linearLayout7 =  LinearLayout<CardView>(880, 40, 290, 0)




    val actionRevealCardButton = Button(
        width = 250,
        height = 80,
        posX = 1650, posY = 600,
        text = "RevealCard", font = Font(34)
    ).apply {
        onMouseClicked = {
            if(cardtoRemove.isNotEmpty()){
                cardtoRemove.clear()
            }
            rootService.playerService.actionRevealCard()

        }
    }
    val playerPassButton = Button(
        width = 220, height = 80,
        posX = 100, posY = 600,
        text = "Pass", font = Font(34)
    ).apply {
        onMouseClicked = {
            rootService.playerService.pass()
            cardtoRemove.clear()
        }
    }





    private val quitButtonInGame = Button(
        width = 220, height = 80,
        posX = 0, posY = 30,
        text = "Quit", font = Font(34)
    ).apply {
        visual = ColorVisual(221, 136, 136)
        onMouseClicked = { exitProcess(0) }
    }

    /**
     * structure to hold pairs of (card, cardView) that can be used
     *
     * 1. to find the corresponding view for a card passed on by a refresh method (forward lookup)
     *
     * 2. to find the corresponding card to pass to a service method on the occurrence of
     * ui events on views (backward lookup).
     */

    init {

        background = ColorVisual(108, 168, 59)

        addComponents(
            drawStack,
            reserveStack,
            linearLayout1,
            linearLayout2,
            linearLayout3,
            linearLayout4,
            linearLayout5,
            linearLayout6,
            linearLayout7,
            actionRevealCardButton,
            playerPassButton,
            quitButtonInGame,
            currentPlayerLabel,
            scorePlayer1Label,
            scorePlayer2Label,
            reservestackLabel,
            drawstackLabel,
        )

    }
    private val cardMap: BidirectionalMap<Card, CardView> = BidirectionalMap()

    /**
     * Initializes the complete GUI, i.e. the four stack views (left, right, played,
     * collected) of each player.
     */

    override fun onGameStart() {
        linearLayout1.clear()
        linearLayout2.clear()
        linearLayout3.clear()
        linearLayout4.clear()
        linearLayout5.clear()
        linearLayout6.clear()
        linearLayout7.clear()
        reserveStack.clear()
        cardMap.clear()
        cardtoRemove.clear()


        val game = rootService.currentGame
        checkNotNull(game) { "No game running" }
        val cardImageLoader = CardImageLoader()

        initializeStackView(game.table.drawPile, drawStack, cardImageLoader)
        currentPlayerLabel.text= "Current Player is:" + rootService.playerService.currentPlayerName()
        scorePlayer1Label.text =
            rootService.currentGame!!.player1.name + " Score is :" + rootService.currentGame!!.player1.score
        scorePlayer2Label.text =
            rootService.currentGame!!.player2.name + " Score is :" + rootService.currentGame!!.player2.score

        drawstackLabel.text= "DrawStack"
        reservestackLabel.text ="ReserveStack"

        initializeView(game.table.pyramid[0],linearLayout1 )
        initializeView(game.table.pyramid[1],linearLayout2 )
        initializeView(game.table.pyramid[2], linearLayout3)
        initializeView(game.table.pyramid[3], linearLayout4)
        initializeView(game.table.pyramid[4], linearLayout5)
        initializeView(game.table.pyramid[5],linearLayout6 )
        initializeView(game.table.pyramid[6],linearLayout7 )


    }
    private fun initializeStackView(stack: Stack<Card>,
                                    stackView: LabeledStackView,
                                    cardImageLoader: CardImageLoader) {

        stack.toList().reversed().forEach { card ->
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            )

            stackView.add(cardView)
            cardMap.add(card to cardView)
        }}
    private fun initializeView( stack: MutableList<Card?>,linearLayout: LinearLayout<CardView>){
        stack.forEach{ card ->
            checkNotNull(card)
            val cardView =createCardView(card).apply {
                onMouseClicked = {
                    if(cardtoRemove.isEmpty()){
                        cardtoRemove.add(0, card)

                    }else{
                        if(cardtoRemove.size<=2){
                            cardtoRemove.add(1, card)
                            rootService.playerService.actionRemovePair(cardtoRemove[0],cardtoRemove[1])
                            cardtoRemove.clear()


                        }
                    }
                }

            }
            if(card.visible){
                cardView.showFront()
            }
            else{
                cardView.showBack()
            }
            linearLayout.add(cardView)
        }
    }

    private fun createCardView(card: Card): CardView {

        val cardImageLoader = CardImageLoader()
        val cardView = CardView(
            width = 120,
            height = 85,
            front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
            back = ImageVisual(cardImageLoader.backImage)
        )

        // turn up the edge cards
        if (card.visible) {
            cardView.showFront()
        } else {
            cardView.showBack()
        }
        cardMap.add(card to cardView)

        return cardView
    }


    /**
     * Updates the events , i.e. the events when a player marks a card.
     */

    override fun onActionRemovePair(card1: Card, card2: Card){

        val game = rootService.currentGame
        checkNotNull(game) { "No game running" }
        val cardView1 = cardMap.forward(card1)
        val cardView2 = cardMap.forward(card2)

        cardView1.removeFromParent()
        cardView2.removeFromParent()
        val pyramid = game.table.pyramid
        for(i in 0..6) {
            pyramid[i].forEach { card ->
                if (card != null) {
                    if (card.visible) {
                        cardMap.forward(card).showFront()
                    }
                }
            }
        }
        currentPlayerLabel.text= "Current Player is:" + rootService.playerService.currentPlayerName()
    }

    override fun updateScore() {
        scorePlayer1Label.text = rootService.currentGame!!.player1.name+
                " Score is :"+ rootService.currentGame!!.player1.score
        scorePlayer2Label.text = rootService.currentGame!!.player2.name +
                " Score is :" + rootService.currentGame!!.player2.score

    }


    override fun onActionSitOut() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game running" }
        if(game.sitOutCount==2){
            rootService.gameService.endGame()
        }
        else{
            currentPlayerLabel.text= "Current Player is:" + rootService.playerService.currentPlayerName()
        }

    }
    override fun onActionDrawCard(){
        val game = rootService.currentGame
        checkNotNull(game) { "No game found." }
        checkNotNull(game.table.drawPile){"Draw stack is Empty"}
        val cardview =cardMap.forward(game.table.reserveStack.peek())
        cardview.showFront()
        cardview.removeFromParent()
        reserveStack.add(cardview)
        currentPlayerLabel.text= "Current Player is:" + rootService.playerService.currentPlayerName()
    }


    override fun reloadscene() {
        cardtoRemove.clear()
    }


}











