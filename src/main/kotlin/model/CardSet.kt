package model

sealed class CardSet(private val values: Set<Cards>) : Set<Cards> by values {

    object Long : CardSet(Cards.values().toSet())

    object Short : CardSet(Cards.values().filter {
            it.value != Cards.Value.SIEBENER && it.value != Cards.Value.ACHTER
        }.toSet()
    )

}