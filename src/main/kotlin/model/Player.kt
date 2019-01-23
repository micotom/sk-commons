package model

import logic.History
import logic.Rule
import logic.Strategy

sealed class Player {

    protected val cardsOnHand: MutableSet<Cards> = mutableSetOf()

    abstract fun findStrategy(history: History, gameType: GameType): Strategy

    fun playCard(cardsOnTable: List<Cards>, history: History, gameType: GameType): Cards {
        return when (cardsOnTable.isEmpty()) {
            true -> cardsOnHand.first()
            false -> {
                val firstCard = cardsOnTable[0]
                val rule = Rule.findRule(firstCard, cardsOnHand, gameType)
                val potentialCards = rule.findCards()
                val selectedCard = findStrategy(history, gameType).selectCard(potentialCards, cardsOnHand, history, gameType)
                cardsOnHand.remove(selectedCard)
                selectedCard
            }
        }
    }

    fun handOverCards(cards: Set<Cards>) {
        cardsOnHand.addAll(cards)
    }

    data class ComputerPlayer(val name: String) : Player() {

        override fun findStrategy(history: History, gameType: GameType): Strategy {
            // TODO
            return Strategy.NaiveFirstInSet()
        }

    }


}