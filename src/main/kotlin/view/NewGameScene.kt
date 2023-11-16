package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.visual.ColorVisual

/**
 * [MenuScene] that is used for starting a new game. It is displayed directly at program start or reached
 * when "new game" is clicked in [GameFinishedMenuScene]. After providing the names of both players,
 * [startButton] can be pressed. There is also a [quitButton] to end the program.
 */
class NewGameScene(private val rootService: RootService):MenuScene(400, 1080), Refreshable {
    private val p1Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 125,
        text = "Player 1:"
    )

    // type inference fails here, so explicit  ": TextField" is required
     val p1Input: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 125

    ).apply {
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank() ||
                    this.text == p2Input.text
        }
    }

    private val p2Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 170,
        text = "Player 2:"
    )

    // type inference fails here, so explicit  ": TextField" is required
    val p2Input: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 170

    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank()||
                    this.text == p1Input.text
        }
    }

    val returnButton = Button(
        width = 140, height = 35,
        posX = 50, posY = 240,
        text = "Return"
    ).apply {
        visual = ColorVisual(221, 136, 136)
    }

     val startButton = Button(
        width = 140, height = 35,
        posX = 210, posY = 240,
        text = "Start Round"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            rootService.gameService.startNewGame(
                p1Input.text.trim(),
                p2Input.text.trim()
            )
        }
    }

    init {
        opacity = .5
        addComponents(
            p1Label, p1Input,
            p2Label, p2Input,
            startButton, returnButton
        )
    }



}