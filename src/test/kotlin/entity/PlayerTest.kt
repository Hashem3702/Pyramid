package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Test cases for [Player]
 */
class PlayerTest {
    val player1 = Player("Hashem")
    val player2 = Player("Kira")

    /**
     * check if the both player's names are written.
     */
    @Test
    fun testName(){
        assertEquals("Hashem",player1.name)
        assertEquals("Kira",player2.name)
    }

    /**
     * check if the both player's points equals zero at the beginning of the Game.
     */

    @Test
    fun testPoints(){
        assertEquals(0,player1.point)
        assertEquals(0,player2.point)
    }


}