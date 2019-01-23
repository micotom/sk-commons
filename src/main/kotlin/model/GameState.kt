package model

class GameState(private val table: Table) {

    private val hiddenCards: MutableSet<Cards> = mutableSetOf()
    private val cardsPlayed: MutableMap<Player, Set<Cards>> = mutableMapOf()
    val cardsOnTable: MutableMap<Player, Cards> = mutableMapOf()

    init {
        hiddenCards.addAll(table.cardSet)
        cardsPlayed[table.player1] = emptySet()
        cardsPlayed[table.player2] = emptySet()
        cardsPlayed[table.player3] = emptySet()
        cardsPlayed[table.player4] = emptySet()
    }

    fun cardPlayed(player: Player, cards: Cards) {
        hiddenCards.remove(cards)
        cardsOnTable[player] = cards
        if (cardsOnTable.size == 4) {
            hiddenCards.addAll(cardsOnTable.values)
            cardsOnTable.clear()
        }
    }

}