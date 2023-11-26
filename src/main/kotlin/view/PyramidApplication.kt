package view

import entity.Player
import service.RootService
import tools.aqua.bgw.core.BoardGameApplication

//
/**
 * Implementation of the BGW [BoardGameApplication] for the example card game "Pyramid"
 */
class PyramidApplication:BoardGameApplication("Pyramid"),Refreshable {

    // Central service from which all others are created/accessed
    // also holds the currently active game
    private val rootService = RootService()

    // This is where the actual game takes place
    private val mainGameScene:MainGameScene = MainGameScene(rootService).apply {
        mainMenuButton.onMouseClicked = {
            this@PyramidApplication.showMenuScene(preGameScene)
        }
    }

    // This menu scene is shown after each finished game (i.e. no more cards to draw)
    private val endGameMenuScene = EndGameScene().apply {
        newGameButton.onMouseClicked = {
            this@PyramidApplication.showMenuScene(newGameScene)
        }
        playAgainButton.onMouseClicked = {
            rootService.gameService.startNewGame(
                player1Name.text,
                player2Name.text
            )
            this@PyramidApplication.hideMenuScene()
        }
        quitButton.onMouseClicked = {
            exit()
        }
    }
    private val preGameScene = PreGameScene().apply {
        playButton.onMouseClicked = {

            this@PyramidApplication.showMenuScene(newGameScene)
        }
        quitButton.onMouseClicked = {
            exit()
        }
    }


    private val newGameScene = NewGameScene(rootService).apply {
        returnButton.onMouseClicked = {
            this@PyramidApplication.hideMenuScene()
            preScene()

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
                preGameScene

            )
            // This is just done so that the blurred background when showing
            // the new game menu has content and looks nicer
            rootService.gameService.startNewGame("Bob", "Alice")

            this.showGameScene(mainGameScene)
            this.showMenuScene(preGameScene, 0)

        }

    override fun refreshAfterNewGame() {
        this.hideMenuScene()
    }

    override fun refreshAfterGameEnd(result:MutableList<Player>) {
        this.showMenuScene(endGameMenuScene)
    }

    /**
     * function to avoide recursive problems
     */
    fun preScene(){
        this.showMenuScene(preGameScene)
    }

}