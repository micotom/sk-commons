package logic

import model.Cards
import model.GameType

sealed class Strategy {

    abstract fun selectCard(candidateCards: Set<Cards>, ownCards: Set<Cards>, othersCards: Set<Cards>,
                   history: History, gameType: GameType): Cards

    class NaiveFirstInSet : Strategy() {

        override fun selectCard(
            candidateCards: Set<Cards>,
            ownCards: Set<Cards>,
            othersCards: Set<Cards>,
            history: History,
            gameType: GameType
        ): Cards {
            return candidateCards.first()
        }

    }

}