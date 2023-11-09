package service

import entity.Card
import entity.CardSuit
import entity.CardValue

import org.junit.jupiter.api.Test

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
        assertFalse(currentGame.reserveStack[1].isFaceUp)
        assertNotEquals(playerBeforeDraw,currentGame.currentPlayer)
        currentGame.drawStack.clear()
        assertFails{mc.spielerService.drawCard()}
        mc.currentGame = null
        assertFails {mc.spielerService.drawCard() }
    }

    /**
     * Method to test the pass function in playerService
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
        mc.currentGame = null
        assertFails {mc.spielerService.pass() }
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
         currentGame.pyramid.forEach { list->
            if(list.first().isFaceUp && list.last().isFaceUp){
                allFaceUp = false
            }

        }
        assertFalse(allFaceUp)

         allFaceUp = true
        mc.spielerService.revealCards()

        currentGame.pyramid.forEach { list->
            if(!list.first().isFaceUp || !list.last().isFaceUp){
                allFaceUp = false
            }
        }
        assertTrue(allFaceUp)
        mc.currentGame = null
        assertFails {mc.spielerService.revealCards() }
    }

    /**
     * method to test the function removePair
     */
    @Test
    fun testRemovePair(){
        val testRefreshable = TestRefreshable()
        val mc = RootService()
        mc.addRefreshable(testRefreshable)

        mc.gameService.startNewGame("Hashem","Kira")
        var currentGame = mc.currentGame
        checkNotNull(currentGame)

        assertFails { mc.spielerService.removePair(Card(CardSuit.SPADES,CardValue.ACE),
            Card(CardSuit.SPADES,CardValue.ACE)) }

        var  card1  = Card(CardSuit.SPADES,CardValue.ACE)
        var  card2  = Card(CardSuit.SPADES,CardValue.ACE)
        card1.isFaceUp = true
        card2.isFaceUp= true

        assertFails { mc.spielerService.removePair(card1,card2) }

        card1 = Card(CardSuit.SPADES,CardValue.EIGHT)
        card2 = Card(CardSuit.SPADES,CardValue.EIGHT)

        card1.isFaceUp = true
        card2.isFaceUp= true

        assertFails { mc.spielerService.removePair(card1,card2) }

        mc.spielerService.drawCard()
        mc.spielerService.drawCard()
        card1 = currentGame.reserveStack[1]
        card2 = Card(CardSuit.SPADES,CardValue.ACE)

        card1.isFaceUp = true
        card2.isFaceUp= true

        assertFails { mc.spielerService.removePair(card1,card2) }

        mc.spielerService.drawCard()
        mc.spielerService.drawCard()
        card1 = Card(CardSuit.SPADES,CardValue.ACE)
        card2 = currentGame.reserveStack[1]

        card1.isFaceUp = true
        card2.isFaceUp= true

        assertFails { mc.spielerService.removePair(card1,card2) }


        mc.currentGame = null
        mc.gameService.startNewGame("Hashem","Kira")
        currentGame = mc.currentGame
        checkNotNull(currentGame)

        card1 = Card(CardSuit.SPADES,CardValue.ACE)
        card2 = Card(CardSuit.SPADES,CardValue.EIGHT)

        card1.isFaceUp = true
        card2.isFaceUp= true

        currentGame.pyramid[0][0] = card1
        currentGame.pyramid[1][0] = card2

        mc.spielerService.removePair(card1,card2)

        if(currentGame.currentPlayer == currentGame.player1){
            currentGame.currentPlayer = currentGame.player2
        }
        assertEquals(1,currentGame.currentPlayer.point)
        assertTrue(currentGame.pyramid[0].isEmpty())
        assertTrue (!currentGame.pyramid[1].contains(card2))
        assertTrue(testRefreshable.refreshAfterTurnCardsCalled)




    }
}