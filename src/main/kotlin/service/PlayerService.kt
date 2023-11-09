package service

import entity.Card


/**
 * Service layer class that provides the logic for the three possible actions a player
 * can take in the game: drawing from stack, passing or removing pair from the pyramid.
 */
class PlayerService (private val rootService: RootService):AbstractRefreshingService(){

    /**
     * this Method enables the player to draw a card from the drawStack
     * instead of choosing two cards from the pyramid
     *
     */
    fun drawCard(){
        val  game = rootService.currentGame
        checkNotNull(game)
        check(game.drawStack.isNotEmpty()){
            "The Stack is empty"
        }
        val card = game.drawStack.removeFirst()
        card.isFaceUp = true
        game.reserveStack.add(0,card)
        if(game.reserveStack.size > 1){
            game.reserveStack[1].isFaceUp = false
        }
        game.passCounter = 0
        changePlayer()
        onAllRefreshables{refreshAfterDrawCard()}

    }

    /**
     * this method changes the turn of the players
     */
    private fun changePlayer(){
        val game = rootService.currentGame
        checkNotNull(game){"There is no game"}
        val player1 = game.player1
        val player2 = game.player2
        if(game.currentPlayer == player1){
            game.currentPlayer = player2
        }
        else {
            game.currentPlayer = player1
        }


        onAllRefreshables{refreshAfterSwitchPlayer()}
    }

    /**
     * the method will be used ,if  one of the players press pass
     */

    fun pass(){
        val game = rootService.currentGame
        checkNotNull(game){"There is no game"}
        game.passCounter += 1
        if(game.passCounter == 2){
            rootService.gameService.endGame()
        }
        else{
            changePlayer()
            onAllRefreshables{refreshAfterPass()}
        }
    }

    /**
     * this Method will be used to delete the chosen Cards form the pyramid
     */

    fun removePair(card1:Card,card2:Card){
        val game = rootService.currentGame
        checkNotNull(game)
        require(checkPair(card1,card2)){
            "The sum of the two cards is not 15"
        }
        if(card1.value.toString() =="A" || card2.value.toString()== "A"){
            game.currentPlayer.point ++
        }
        else{
          game.currentPlayer.point += 2
        }
        if(game.reserveStack.contains(card1)){
            game.reserveStack.removeFirst()
            // remove Card2 from the pyramid
            for(list in game.pyramid){
                if(list.contains(card2)){
                    list.remove(card2)
                }

            }

        }
        else if(game.reserveStack.contains(card2)){
            game.reserveStack.removeFirst()
            for(list in game.pyramid){
                if(list.contains(card1)){
                    list.remove(card1)
                }
            }
        }
        else{
            // remove card1 and card2 from the pyramid
            for(list in game.pyramid){
                if(list.contains(card1)){
                    list.remove(card1)
                }
                if(list.contains(card2)){
                    list.remove(card2)
                }

            }
        }
        if(pyramidIsEmpty(game.pyramid)){
            rootService.gameService.endGame()
        }
        else{
            revealCards()
            game.passCounter = 0
            changePlayer()
            onAllRefreshables { refreshAfterRemovePair(card1,card2) }
        }

    }

    /**
     * This Method helps to check ,if the chosen cards can be taken or not
     */
    private fun checkPair(card1:Card,card2:Card):Boolean {
        val game = rootService.currentGame
        checkNotNull(game)
        if (!card1.isFaceUp ||
            !card2.isFaceUp ||
            card1.value.toString() == card2.value.toString()
        ) {
            return false
        }
        if (card1.value.toString() == "A" && card2.value.toString() == "A") {
            return false
        }
        if (card1.value.toInt() + card2.value.toInt() != 15 &&
            card1.value.toString() != "A" &&
            card2.value.toString() != "A"
        )
         {
            return false
        }
        if(game.reserveStack.contains(card1) && game.reserveStack.first() != card1) return false

        return !(game.reserveStack.contains(card2) && game.reserveStack.first() != card2)
    }

    /**
     * this method checks ,if the faces of the first and the last elements of all
     * the Lists of the pyramid  are open or not ... if not then isFaceUp will be true
     */
     fun revealCards(){
        // die Implementer der Methode
        val game = rootService.currentGame
        checkNotNull(game){"There is no game"}
        for(listInPyramid in game.pyramid){
            if(listInPyramid.isNotEmpty()) {
                if (!listInPyramid.first().isFaceUp) {
                    game.pyramid[game.pyramid.indexOf(listInPyramid)].first().isFaceUp = true
                    //  listInPyramid.first().isFaceUp == true
                }
                if (!listInPyramid.last().isFaceUp) {
                    game.pyramid[game.pyramid.indexOf(listInPyramid)].last().isFaceUp = true
                    // listInPyramid.last().isFaceUp == true
                }
            }
            onAllRefreshables { refreshAfterTurnCards(listInPyramid) }
        }
    }

    /**
     * Method to check if the pyramid is empty or not
     */
    private fun pyramidIsEmpty(pyramid:MutableList<MutableList<Card>>):Boolean{
        var result = true
        for(list in pyramid){
            if(list.isNotEmpty()){
                result = false
                break
            }
        }
        return result
    }
}