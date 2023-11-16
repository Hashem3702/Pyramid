package view

import entity.Game
import entity.Player
import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * [MenuScene] that is displayed when the game is finished. It shows the final result of the game
 * as well as the score. Also, there are two buttons: one for starting a new game and one for
 * quitting the program.
 */
class EndGameScene(private val rootService: RootService): MenuScene(400, 1080), Refreshable {

    private val p2Score = Label(width = 300, height = 35, posX = 50, posY = 125)

    private val p1Score = Label(width = 300, height = 35, posX = 50, posY = 160)

    private val gameResult = Label(width = 300, height = 35, posX = 50, posY = 195).apply {
    }

    val quitButton = Button(width = 140, height = 35, posX = 50, posY = 265, text = "Quit").apply {
        visual = ColorVisual(Color(221,136,136))
    }

    val newGameButton = Button(width = 140, height = 35, posX = 210, posY = 265, text = "New Game").apply {
        visual = ColorVisual(Color(136, 221, 136))
    }
    val playAgainButton = Button(width = 140, height = 35, posX = 70, posY = 200, text = "Play Again").apply {
        visual = ColorVisual(Color(0, 140, 150))
    }

    init {
        opacity = .5
        addComponents( p1Score, p2Score, gameResult, newGameButton, quitButton,)
    }

    private fun Player.scoreString(): String = "${this.name} : ${this.point} points."

    private fun Game.gameResultString(): String {
        val p1Score = player1.point
        val p2Score = player2.point
        return when {
            p1Score - p2Score > 0 -> "${player1.name} Won!"
            p1Score - p2Score < 0 -> "${player2.name} Won!"
            else -> "Draw. No winner."
        }
    }
    override fun refreshAfterGameEnd(result:List<Player>) {
        val game = rootService.currentGame
        checkNotNull(game) { "No game running" }
        p1Score.text = game.player1.scoreString()
        p2Score.text = game.player2.scoreString()
        gameResult.text = game.gameResultString()
    }
}