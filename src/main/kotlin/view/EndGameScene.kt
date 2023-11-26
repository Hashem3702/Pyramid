package view


import entity.Player

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * [MenuScene] that is displayed when the game is finished. It shows the final result of the game
 * as well as the score. Also, there are two buttons: one for starting a new game and one for
 * quitting the program.
 */
class EndGameScene(): MenuScene(700, 1080), Refreshable {

    private val p2Score = Label(width = 500, height = 60, posX = 50, posY = 150,
        alignment = Alignment.CENTER,
        font = Font(24.0, Color.BLACK,"Open Sans", Font.FontWeight.BOLD))

    private val p1Score = Label(width = 500, height = 60, posX = 50, posY = 250,
        alignment = Alignment.CENTER,
        font = Font(24.0, Color.BLACK,"Open Sans", Font.FontWeight.BOLD))
    val player1Name = Label()
    val player2Name = Label()

    private val gameResult = Label(width = 600, height = 100, posX = 50, posY = 50,
        alignment = Alignment.CENTER,
        font = Font(24.0, Color.GREEN,"Open Sans", Font.FontWeight.BOLD)).apply {

    }

    val quitButton = Button(width = 300, height = 85, posX = 30, posY = 500, text = "Quit",
        font = Font(24.0, Color.WHITE,"Open Sans", Font.FontWeight.BOLD),
        visual = ColorVisual(Color(0xFF7261))).apply {

    }

    val newGameButton = Button(width = 300, height = 85, posX = 370, posY = 500, text = "New Game",
        font = Font(24.0, Color.WHITE,"Open Sans",Font.FontWeight.BOLD),
        visual = ColorVisual(Color(0x81BA29))).apply {  }

    val playAgainButton = Button(width = 300, height = 85, posX = 200, posY = 400, text = "Play Again",
        font = Font(24.0, Color.WHITE,"Open Sans",Font.FontWeight.BOLD),
        visual = ColorVisual(Color(0X69E4FF))).apply {

    }


    init {
        opacity = .5
        addComponents( p1Score, p2Score, gameResult, newGameButton, quitButton,playAgainButton)
    }

    override fun refreshAfterGameEnd(result:MutableList<Player>) {
        player1Name.text = result[0].name
        player2Name.text = result[1].name
        p1Score.text = "${result[0].name} : ${result[0].point} points."
        p2Score.text = "${result[1].name} : ${result[1].point} points."
        if (result[0].point == result[1].point){
            gameResult.text = " NO one Won!"
        }
        else if (result[1].point > result[0].point) {
            gameResult.text = " ${result[1].name} Won!"
        }
        else{
            gameResult.text = " ${result[0].name} Won!"
        }


    }
}