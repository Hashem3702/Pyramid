package view

import entity.Player
import service.RootService
import tools.aqua.bgw.core.BoardGameApplication

class PyramidApplication():BoardGameApplication("Pyramid"),Refreshable {

    // Central service from which all others are created/accessed
    // also holds the currently active game
    private val rootService = RootService()

    // This is where the actual game takes place
    private val mainGameScene:MainGameScene = MainGameScene(rootService)

    // This menu scene is shown after each finished game (i.e. no more cards to draw)
    private val endGameMenuScene = EndGameScene(rootService).apply {
        newGameButton.onMouseClicked = {
            this@PyramidApplication.showMenuScene(newGameScene)
        }
        quitButton.onMouseClicked = {
            exit()
        }
    }

    // This menu scene is shown after application start and if the "new game" button
    // is clicked in the EndGameScene
    private val newGameScene = NewGameScene(rootService).apply {
        returnButton.onMouseClicked = {
            exit()
        }

    }


        init {

            // all scenes and the application itself need too
            // react to changes done in the service layer
            rootService.addRefreshables(
                this,
                newGameScene,
                endGameMenuScene,
                mainGameScene,

            )
            // This is just done so that the blurred background when showing
            // the new game menu has content and looks nicer
            rootService.gameService.startNewGame("Bob", "Alice")

            this.showGameScene(mainGameScene)
            this.showMenuScene(newGameScene, 0)
        }

    override fun refreshAfterNewGame() {
        this.hideMenuScene()
    }

    override fun refreshAfterGameEnd(result:List<Player>) {
        this.showMenuScene(endGameMenuScene)
    }

}