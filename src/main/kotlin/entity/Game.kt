package entity

/**
 *Entity class that represents a game state of "Pyramide"
 */
data class Game (val player1:Player,val player2:Player, var currentPlayer: Player,
                 var reserveStack:MutableList<Card>,var drawStack:MutableList<Card>
                 ,var pyramide:MutableList<Card>)  {

    var passCounter: Int = 0

}



