package entity

/**
 * Data class for the single typ of game elements that the game "Pyramide" knows: cards.
 *
 * It is characterized by a [CardSuit] and a [CardValue]
 */
 data class Card(val suit: CardSuit, val value: CardValue) {
    var isFaceUp = false
    override fun toString(): String = "$suit$value"

}
