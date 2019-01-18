package model

sealed class GameType(open val cardSet: CardSet) {

    protected abstract val filterTrumps: (Cards) -> Boolean

    val trumps: Set<Cards> by lazy { cardSet.filter(filterTrumps).toSet() }
    val nonTrumps by lazy {
        cardSet.filter { !trumps.contains(it) }
    }

    sealed class Solo(cardSet: CardSet) : GameType(cardSet) {

        data class FarbSolo(val color: Cards.Color, override val cardSet: CardSet) : Solo(cardSet) {
            override val filterTrumps = { card: Cards ->
                card.color == color || card.value == Cards.Value.UNTER || card.value == Cards.Value.OBER
            }
        }

        data class Wenz(override val cardSet: CardSet) : Solo(cardSet) {
            override val filterTrumps = { card: Cards ->
                card.value == Cards.Value.UNTER
            }
        }

        data class FarbWenz(val color: Cards.Color, override val cardSet: CardSet) : Solo(cardSet) {
            override val filterTrumps = { card: Cards ->
                card.color == color || card.value == Cards.Value.UNTER
            }
        }

        data class Geier(override val cardSet: CardSet) : Solo(cardSet) {
            override val filterTrumps = { card: Cards ->
                card.value == Cards.Value.OBER
            }
        }

        data class FarbGeier(val color: Cards.Color, override val cardSet: CardSet) : Solo(cardSet) {
            override val filterTrumps = { card: Cards ->
                card.color == color || card.value == Cards.Value.OBER
            }
        }

    }

    data class SauSpiel(val rufSau: Cards, override val cardSet: CardSet) : GameType(cardSet) {

        init {
            when (rufSau.value) {
                Cards.Value.AS -> when (rufSau) {
                    Cards.HERZ_AS -> throw IllegalArgumentException()
                    else -> Unit
                }
                else -> throw IllegalArgumentException()
            }
        }

        override val filterTrumps = { card: Cards ->
            card.color == Cards.Color.HERZ || card.value == Cards.Value.OBER || card.value == Cards.Value.UNTER
        }
    }

}