package entity

/**
 *Entity class that represents a game state of "Pyramide"
 */
data class Game (val player1:Player,val player2:Player
                 ,var currentPlayer: Player
                 ,val pyramid:MutableList<MutableList<Card>>
                 ,val drawStack:MutableList<Card>)  {

    val reserveStack:MutableList<Card> = mutableListOf()
    var passCounter: Int = 0

}



