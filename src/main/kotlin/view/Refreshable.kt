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


  fun refreshAfterDrawCard():Unit{}

  fun refreshAfterRemovePair(card1:Card,card2:Card):Unit{}

  fun refreshAfterTurnCards(card:List<Card>):Unit{}

  fun refreshAfterSwitchPlayer():Unit{}

  fun refreshAfterPass():Unit{}

  fun refreshAfterGameEnd(result:List<Player>):Unit{}


}