package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Testcases for [Game]
 */
class GameTest {
    val player1 = Player("Hashem")
    val player2 = Player("Kira")
    var drawStack = mutableListOf<Card>()
    var pyramid = mutableListOf<MutableList<Card>>()

    val game = Game(player1,player2,player1,pyramid,drawStack)

    /**
     * test if tha passCounter starts from zero
     */
    @Test
    fun testPassCounter(){
        assertEquals(0,game.passCounter)
    }

    /**
     * check if the both players didn't forget to write their names
     */
    @Test
    fun testPlayersNames(){
        assertTrue { game.player1.name.length > 0 && game.player2.name.length > 0  }
        assertNotNull(game.player1)
        assertNotNull(game.player2)
    }







}