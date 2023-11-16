package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.visual.ColorVisual

class PreGameScene(private val rootService: RootService): MenuScene(400, 1080), Refreshable {
    val quitButton = Button(
        width = 140, height = 35,
        posX = 50, posY = 240,
        text = "Quit"
    ).apply {
        visual = ColorVisual(221, 136, 136)
    }

    val playButton = Button(
        width = 140, height = 35,
        posX = 210, posY = 240,
        text = "Play"
    ).apply {
        visual = ColorVisual(136, 221, 136)
    }

    init {
        opacity = .5
        addComponents(
        quitButton,playButton
        )
    }
}