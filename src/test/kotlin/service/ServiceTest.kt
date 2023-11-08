package service

import entity.Card
import org.junit.jupiter.api.Test
import view.Refreshable
import kotlin.test.*


/**
 * Class that provides tests for [GameService] and [PlayerService] (both at the same time,
 * as their functionality is not easily separable) by basically playing through some sample games.
 * [TestRefreshable] is used to validate correct refreshing behavior even though no GUI
 * is present.
 */
class ServiceTest {


    /**
     * Tests the default case of starting a game: instantiate a [RootService] and then run
     * startNewGame on its [RootService.gameService].
     */
    @Test
    fun testStartNewGame(){
        val testRefreshable = TestRefreshable()
        val mc = RootService()
        mc.addRefreshable(testRefreshable)

        assertFalse(testRefreshable.refreshAfterNewGameCalled)
        assertNull(mc.currentGame)
        mc.gameService.startNewGame("Hashem","Kira")
        assertTrue(testRefreshable.refreshAfterNewGameCalled)
        assertNotNull(mc.currentGame)

        val currentGame= mc.currentGame
        assertNotEquals(currentGame!!.player1.name,currentGame.player2.name)
        assertNotNull(currentGame.currentPlayer)
        assertEquals(7,currentGame.pyramid[6].size)
        assertEquals(24,currentGame.drawStack.size)
    }

    /**
     * check the endGame method
     */
    @Test
    fun testEndGame(){

        val testRefreshable = TestRefreshable()
        val mc = RootService()
        mc.addRefreshable(testRefreshable)

        mc.gameService.startNewGame("Hashem","Kira")
        val currentGame = mc.currentGame
        assertTrue(testRefreshable.refreshAfterNewGameCalled)
        assertNotNull(currentGame)

        currentGame.player1.point =1
        mc.gameService.endGame()
        assertTrue { testRefreshable.refreshAfterGameEndCalled }
        assertNull(mc.currentGame)
    }
    /**
     * Method to test drawCard in the PlayerService
     */
    @Test
    fun testDrawCard(){
        val testRefreshable = TestRefreshable()
        val mc = RootService()
        mc.addRefreshable(testRefreshable)

        mc.gameService.startNewGame("Hashem","Kira")
        val currentGame = mc.currentGame
        assertTrue(testRefreshable.refreshAfterNewGameCalled)
        assertNotNull(currentGame)

        var firstDrawStack = currentGame.drawStack.first()
        var playerBeforeDraw = currentGame.currentPlayer

        mc.spielerService.drawCard()
        assertEquals(firstDrawStack,currentGame.reserveStack.first())
        assertNotEquals(firstDrawStack,currentGame.drawStack.first())
        assertTrue(currentGame.reserveStack.first().isFaceUp)
        assertNotEquals(playerBeforeDraw,currentGame.currentPlayer)

        playerBeforeDraw = currentGame.currentPlayer
        firstDrawStack = currentGame.drawStack.first()
        mc.spielerService.drawCard()

        assertEquals(firstDrawStack,currentGame.reserveStack.first())
        assertNotEquals(firstDrawStack,currentGame.drawStack.first())
        assertEquals(false,currentGame.reserveStack[1].isFaceUp)
        assertNotEquals(playerBeforeDraw,currentGame.currentPlayer)
    }

    /**
     * Method to test the pass funktion in playerService
     */
    @Test
    fun testPass(){
        val testRefreshable = TestRefreshable()
        val mc = RootService()
        mc.addRefreshable(testRefreshable)

        mc.gameService.startNewGame("Hashem","Kira")
        val currentGame = mc.currentGame
        checkNotNull(currentGame)
        val currentPlayer = currentGame.currentPlayer

        mc.spielerService.pass()

        assertEquals(1,currentGame.passCounter)
        assertNotEquals(currentPlayer,currentGame.currentPlayer)

        mc.spielerService.pass()
        assertTrue (testRefreshable.refreshAfterGameEndCalled)
        assertTrue(testRefreshable.refreshAfterPassCalled)
    }

    /**
     * Method for testing the revealCards function
     */
    @Test
    fun testReveal(){
        val testRefreshable = TestRefreshable()
        val mc = RootService()
        mc.addRefreshable(testRefreshable)

        mc.gameService.startNewGame("Hashem","Kira")
        val currentGame = mc.currentGame
        checkNotNull(currentGame)

        var allFaceUp = true
        mc.spielerService.revealCards()

        currentGame.pyramid.forEach { mulist->
            if(!mulist.first().isFaceUp || !mulist.last().isFaceUp){
                allFaceUp = false
            }
        }
        assertTrue(allFaceUp)
    }
}