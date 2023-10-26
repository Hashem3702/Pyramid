package entity

/**
 *
 */
data class Game (val player1:Player,val player2:Player, var currentPlayer: Player,
                 var passCounter: Int,var reserveStack:List<Card>,var drawStack:List<Card>,var pyramide:List<Card> ,
)