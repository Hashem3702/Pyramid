package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * this is the preScene that will be seen when game starts
 */
class PreGameScene(): MenuScene(600, 1080), Refreshable {
    val quitButton = Button(
        width = 300, height = 85,
        posX = 150, posY = 500,
        text = "Quit",
        font = Font(24.0, Color.WHITE,"Open Sans", Font.FontWeight.BOLD)
    ).apply {
        visual = ColorVisual(Color(0xFF7261))
    }

    val playButton = Button(
        width = 400, height = 120,
        posX = 100, posY = 240,
        text = "Play",
        font = Font(24.0, Color.WHITE,"Open Sans", Font.FontWeight.BOLD)
    ).apply {
        visual = ColorVisual(Color(0x81BA29))
    }

    init {
        opacity = .5
        addComponents(
        quitButton,playButton
        )
    }
}