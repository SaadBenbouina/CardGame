package view
import tools.aqua.bgw.core.BoardGameApplication
import service.*
import entity.*
/**
 * Implementation of the BGW [BoardGameApplication] for the example card game "War"
 */
class PyramideApplication: BoardGameApplication("Pyramide"), Refreshable {


    // Central service from which all others are created/accessed
    // also holds the currently active game
    private val rootService = RootService()

    // Scenes

    // This is where the actual game takes place
    private val gameScene = GameScene(rootService)

    // This menu scene is shown after each finished game (i.e. no more cards to draw)
    private val gameFinishedMenuScene = ScoreBoardScene(rootService).apply {
        newGameButton.onMouseClicked = {
            this@PyramideApplication.showMenuScene(newGameMenuScene)
        }
        quitButton.onMouseClicked = {
            exit()
        }
    }

    // This menu scene is shown after application start and if the "new game" button
    // is clicked in the gameFinishedMenuScene
    private val newGameMenuScene = NewGameMenuScene(rootService).apply {
        quitButton.onMouseClicked = {
            exit()
        }
    }

    init {

        // all scenes and the application itself need too
        // to react to changes done in the service layer
        rootService.addRefreshables(
            this,
            gameScene,
            gameFinishedMenuScene,
            newGameMenuScene
        )


        // This is just done so that the blurred background when showing
        // the new game menu has content and looks nicer
        rootService.gameService.startNewGame(createPlayerList(Player("bob"),Player("jack")))

        this.showGameScene(gameScene)
        this.showMenuScene(newGameMenuScene, 0)

    }
    private fun createPlayerList(player1:Player,player2:Player): List<Player> {
        val players: MutableList<Player> = mutableListOf()

        players.add(player1)
        players.add(player2)

        return players.toList()
    }
    override fun onGameStart(){
        this.hideMenuScene()
    }

    override fun onGameFinished() {
        this.showMenuScene(gameFinishedMenuScene)
    }

}


