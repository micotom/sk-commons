package logic

import arrow.core.None
import arrow.core.Option
import model.Cards
import model.GameType

sealed class Rule(firstCard: Cards, gameType: GameType) : () -> Option<Set<Cards>> {

    abstract fun applies(): Boolean

    abstract fun findCards(): Set<Cards>

    protected val firstCardIsTrump = gameType.trumps.contains(firstCard)

    override fun invoke() = when (applies()) {
        true -> Option.just(findCards())
        false -> None
    }

    class GiveColor(private val firstCard: Cards, private val gameType: GameType, private val ownCards: Set<Cards>) :
        Rule(firstCard, gameType) {

        override fun applies() = when (firstCardIsTrump) {
            true -> false
            else -> {
                ownCards.any { !gameType.trumps.contains(it) && it.color == firstCard.color }
            }
        }

        override fun findCards() = ownCards.filter { firstCard.color == it.color }.toSet()

    }

    class GiveTrump(firstCard: Cards, private val gameType: GameType, private val ownCards: Set<Cards>) :
        Rule(firstCard, gameType) {

        override fun applies() = when (firstCardIsTrump) {
            false -> false
            else -> ownCards.any { gameType.trumps.contains(it) }
        }

        override fun findCards() = ownCards.filter { gameType.trumps.contains(it) }.toSet()

    }

    class GiveFree(private val firstCard: Cards, private val gameType: GameType, private val ownCards: Set<Cards>) :
        Rule(firstCard, gameType) {

        override fun applies() = when (firstCardIsTrump) {
            true -> ownCards.none { gameType.trumps.contains(it) }
            else -> ownCards.asSequence().filter { !gameType.trumps.contains(it) }.none { firstCard.color == it.color }
        }

        override fun findCards() = ownCards

    }

}