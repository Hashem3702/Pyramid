package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotSame
import kotlin.test.assertTrue
import kotlin.test.*

/**
 * Test cases for [Card]
 */
class CardTest {
    private val aceOfSpaces = Card(CardSuit.SPADES,CardValue.ACE)
    private val jackOfClubs = Card(CardSuit.CLUBS,CardValue.JACK)
    private val queenOfHeart = Card(CardSuit.HEARTS,CardValue.QUEEN)
    private val otherQueenOfHeart = Card(CardSuit.HEARTS,CardValue.QUEEN)
    private val jackOfDiamonds = Card(CardSuit.DIAMONDS, CardValue.JACK)

    // unicode characters for the suits, as those should be used by [WarCard.toString]
    private val heartsChar = '\u2665' // ♥
    private val diamondsChar = '\u2666' // ♦
    private val spadesChar = '\u2660' // ♠
    private val clubsChar = '\u2663' // ♣

    /**
     * Check if to String produces the correct strings for some test cards
     * for all four suits.
     */
    @Test
    fun testToString(){
        assertEquals(spadesChar + "A",aceOfSpaces.toString())
        assertEquals(clubsChar + "J",jackOfClubs.toString())
        assertEquals(heartsChar + "Q",queenOfHeart.toString())
        assertEquals(diamondsChar + "J",jackOfDiamonds.toString())
    }

    /**
     * Check if toString produces a 2 character string for every possible card
     * except the 10 (for which length=3 is ensured)
     */
    @Test
    fun testToStringLength() {
        CardSuit.values().forEach { suit ->
            CardValue.values().forEach { value ->
                if (value == CardValue.TEN)
                    assertEquals(3, Card(suit, value).toString().length)
                else
                    assertEquals(2, Card(suit, value).toString().length)
            }
        }
    }

    /**
     * Check if two cards with the same CardSuit/CardValue combination are equal
     * in the sense of the `==` operator, but not the same in the sense of
     * the `===` operator.
     */
    @Test
    fun testEquals() {
        assertEquals(queenOfHeart, otherQueenOfHeart)
        assertNotSame(queenOfHeart, otherQueenOfHeart)
    }



}