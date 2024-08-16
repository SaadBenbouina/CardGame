package view

import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import service.*
import entity.*
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * [MenuScene] that is used for starting a new game. It is displayed directly at program start or reached
 * when "new game" is clicked. After providing the names of both players,
 * [startButton] can be pressed. There is also a [quitButton] to end the program.
 */
class NewGameMenuScene(private val rootService: RootService) : MenuScene(400, 1080), Refreshable {

    private val headlineLabel = Label(
        width = 300, height = 50, posX = 50, posY = 50,
        text = "Start New Game",
        font = Font(size = 22)
    )

    private val p1Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 125,
        text = "Player 1:"
    )

    // type inference fails here, so explicit  ": TextField" is required
    // see https://discuss.kotlinlang.org/t/unexpected-type-checking-recursive-problem/6203/14
    private val p1Input: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 125,
        text = listOf("Homer", "Marge", "Bart", "Lisa", "Maggie").random()
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank()
        }
    }

    private val p2Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 170,
        text = "Player 2:"
    )

    // type inference fails here, so explicit  ": TextField" is required
    // see https://discuss.kotlinlang.org/t/unexpected-type-checking-recursive-problem/6203/14
    private val p2Input: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 170,
        text = listOf("Fry", "Bender", "Leela", "Amy", "Zoidberg").random()
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank()
        }
    }

    val quitButton = Button(
        width = 140, height = 35,
        posX = 50, posY = 240,
        text = "Quit"
    ).apply {
        visual = ColorVisual(221, 136, 136)
    }

    private val startButton = Button(
        width = 140, height = 35,
        posX = 210, posY = 240,
        text = "Start"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        val playerList = createPlayerList()
        onMouseClicked = {
            rootService.gameService.startNewGame(playerList)
        }
    }
    /**
     * Generates a List of player  from the input textboxes of the StarNewGameScene
     * @return a list of Players with all players in it.
     */
    private fun createPlayerList(): List<Player> {
        val players: MutableList<Player> = mutableListOf()

        if (p1Input.text.trim().isNotBlank()) players.add(Player(p1Input.text.trim()))
        if (p2Input.text.trim().isNotBlank()) players.add(Player(p2Input.text.trim()))

        return players.toList()
    }


    init {
        opacity = .5
        addComponents(
            headlineLabel,
            p1Label, p1Input,
            p2Label, p2Input,
            startButton, quitButton
        )
    }
}