package service

import entity.Card
import entity.Player
import view.Refreshable

/**
 * [Refreshable] implementation that refreshes nothing, but remembers
 * if a refresh method has been called (since last [reset])
 */
class TestRefreshable:Refreshable {
    var refreshAfterNewGameCalled : Boolean = false
        private set
    var refreshAfterDrawCardCalled : Boolean = false
        private set
    var refreshAfterRemovePairCalled : Boolean = false
        private set
    var refreshAfterTurnCardsCalled : Boolean = false
        private set
    var refreshAfterSwitchPlayerCalled : Boolean = false
        private set
    var refreshAfterPassCalled : Boolean = false
        private set
    var refreshAfterGameEndCalled : Boolean = false
        private set

    /**
     * resets all *Called properties to false
     */
    fun reset(){
        refreshAfterNewGameCalled = false
        refreshAfterDrawCardCalled = false
        refreshAfterRemovePairCalled = false
        refreshAfterPassCalled = false
        refreshAfterGameEndCalled = false
        refreshAfterSwitchPlayerCalled = false
        refreshAfterTurnCardsCalled = false
    }

    override fun refreshAfterNewGame() {
        refreshAfterNewGameCalled = true
    }

    override fun refreshAfterDrawCard() {
        refreshAfterDrawCardCalled = true
    }

    override fun refreshAfterPass() {
        refreshAfterPassCalled = true
    }

    override fun refreshAfterSwitchPlayer() {
        refreshAfterSwitchPlayerCalled
    }

    override fun refreshAfterGameEnd(result: List<Player>) {
        refreshAfterGameEndCalled = true
    }

    override fun refreshAfterRemovePair(card1: Card, card2: Card) {
        refreshAfterRemovePairCalled = true
    }

    override fun refreshAfterTurnCards(card: List<Card>) {
        refreshAfterTurnCardsCalled = true
    }

}