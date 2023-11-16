package service
import entity.*
import kotlin.random.Random

/**
 * Service layer class that provides the logic for actions not directly
 * related to a single player.
 */
class GameService (private val rootService: RootService):AbstractRefreshingService(){

    /**
     * this Method creates the 52 Cards of the Game and put the in a list
     */
    fun createCards(): MutableList<Card> {
        val list = mutableListOf<Card>()
        repeat(52) { index ->
            list.add(
                Card(
                    CardSuit.values()[index / 13],
                    CardValue.values()[(index % 13)]
                )
            )
        }
        list.shuffle()
        return list
    }



/**
 * Starts a new game (overwriting a currently active one, if it exists)
 *
 * @param player1Name name of Player 1
 * @param player2Name name of Player 2
 * @param cards is a list of the 52 cards
 */
    fun startNewGame(player1Name:String ,
                     player2Name:String,
                     cards:MutableList<Card> = createCards()
    ):Unit{
        require(player1Name != player2Name
                && player1Name.isNotEmpty()
                && player2Name.isNotEmpty()){
            "The names have to different andnot empty"
        }

        val player1 = Player(player1Name)
        val player2 = Player(player2Name)

        val currentPlayer = mutableListOf<Player>(player1,player2).random()

        val game = Game(player1,
                        player2,
                        currentPlayer,
                        initialisePyramid(cards),
                        cards
            )
        rootService.currentGame = game
        rootService.spielerService.revealCards()
        onAllRefreshables { refreshAfterNewGame() }
    }

    /**
     * method for initialising the pyramid of the game
     */
    private fun initialisePyramid(allCards:MutableList<Card>):MutableList<MutableList<Card>>{
        val pyramid : MutableList<MutableList<Card>> = MutableList(7){ mutableListOf() }
        for(list in pyramid) {
            for (index in 0..pyramid.indexOf(list)) {
                list.add(allCards.first())
                allCards.removeFirst()
            }
        }

        return pyramid
    }


    /**
     * this method ends the game... at first will be checked how is the winner and then
     * the game will be ended
     */
    fun endGame():Unit{
        val game = rootService.currentGame
        checkNotNull(game)
        val playerList = mutableListOf<Player>(game.player1,game.player2)

            onAllRefreshables { refreshAfterGameEnd(playerList) }

        rootService.currentGame = null
    }

}