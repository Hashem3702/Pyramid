package view

import service.AbstractRefreshingService
import entity.Card
import entity.Player

/**
 * This interface provides a mechanism for the service layer classes to communicate
 * (usually to the view classes) that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 *
 * Default (empty) implementations are provided for all methods, so that implementing
 * UI classes only need to react to events relevant to them.
 *
 * @see AbstractRefreshingService
 *
 */

interface Refreshable {

  /**
   * perform refreshes that are necessary after a new game started
   */
  fun  refreshAfterNewGame():Unit{}


  /**
   * perform refreshes that are necessary after a card has been drawend
   */
  fun refreshAfterDrawCard():Unit{}

  /**
   * perform refreshes that are necessary after the pair have been removed
   */

  fun refreshAfterRemovePair(card1:Card,card2:Card):Unit{}

  /**
   * perform refreshes that are necessary after revealcards
   */

  fun refreshAfterTurnCards(card:List<Card>):Unit{}

  /**
   * perform refreshes that are necessary after switch players
   */

  fun refreshAfterSwitchPlayer():Unit{}

  /**
   * perform refreshes that are necessary after presseing pass
   */

  fun refreshAfterPass():Unit{}

  /**
   * perform refreshes that are necessary after the end of the game
   */

  fun refreshAfterGameEnd(result:MutableList<Player>):Unit{}


}